package com.aruma.mock.authone.account.service.impl;

import com.aruma.mock.authone.account.beans.dto.AccountDTO;
import com.aruma.mock.authone.account.beans.model.Account;
import com.aruma.mock.authone.account.beans.model.Credentials;
import com.aruma.mock.authone.account.beans.model.Document;
import com.aruma.mock.authone.account.beans.model.User;
import com.aruma.mock.authone.account.exception.AccountException;
import com.aruma.mock.authone.account.exception.CodiceFiscaleException;
import com.aruma.mock.authone.account.extractor.CodiceFiscaleExtractor;
import com.aruma.mock.authone.account.repository.AccountRepository;
import com.aruma.mock.authone.account.repository.mapper.AccountMapper;
import com.aruma.mock.authone.account.service.AccountService;
import com.aruma.mock.authone.account.service.CredentialsService;
import com.aruma.mock.authone.account.service.DocumentService;
import com.aruma.mock.authone.account.service.UserService;
import com.aruma.mock.authone.account.factory.AccountBuilder;
import com.aruma.mock.authone.core.utils.enums.AccountStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final UserService userService;
    private final AccountRepository accountRepository;
    private final DocumentService documentService;
    private final CredentialsService credentialsService;
    AccountMapper accountMapper = AccountMapper.INSTANCE;

    public AccountServiceImpl(UserService userService, AccountRepository accountRepository, DocumentService documentService, CredentialsService credentialsService) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.documentService = documentService;
        this.credentialsService = credentialsService;
    }


    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        logger.debug("Creating account");

        AccountBuilder factory = new AccountBuilder(userService, documentService, credentialsService);
        String codiceFiscale = accountDTO.getUser().getCodiceFiscale();

        User user = userService.findByCf(codiceFiscale);
        checkIfAlreadyExist(user);

        Boolean usernameAlreadyTaken = credentialsService.checkIfExist(accountDTO.getUser().getCredentials().getUsername());
        if (usernameAlreadyTaken) throw new AccountException("Username already taken.", "011");

        Account account = factory
                .initialize()
                .user(accountDTO.getUser())
                .document(accountDTO.getDocument())
                .activate()
                .getInstance();

        account = save(account);
        AccountDTO savedAccount = accountMapper.entityToDto(account);
        logger.debug("Account created successfully");
        return savedAccount;
    }

    @Override
    public AccountDTO activate(MultipartFile file, String codiceFiscale) {
        logger.debug("Activating account for codice fiscale: {}", codiceFiscale);
        User user = userService.findByCf(codiceFiscale);
        Account account;
        AccountStatus currentAccountStatus;

        if (user == null)
            throw new CodiceFiscaleException("User associated with Fiscal Code: " + codiceFiscale + " not found.", "002");

        account = accountRepository.findByUser(user);
        currentAccountStatus = account.getAccountStatus();

        if (currentAccountStatus.equals(AccountStatus.ACTIVE))
            throw new AccountException("Account already active.", "005");

        try {
            String cfFromFile = CodiceFiscaleExtractor.extractCodiceFiscaleFromP7M(file.getBytes());
            boolean verified = CodiceFiscaleExtractor.verifyCodiceFiscale(cfFromFile, codiceFiscale);

            if (verified) {
                Document document = documentService.store(file);
                account.setDocument(document);
                updateAccountStatus(account, AccountStatus.ACTIVE);
            }
            AccountDTO updatedAccountDTO = accountMapper.entityToDto(findById(account.getId()));
            logger.debug("Account activated successfully");
            return updatedAccountDTO;
        } catch (IOException e) {
            throw new CodiceFiscaleException(e.getMessage(), "500");
        }
    }

    @Override
    public void updateAccountStatus(Account account, AccountStatus newAccountStatus) {
        account.setAccountStatus(newAccountStatus);
        accountRepository.save(account);
        logger.debug("Account status updated: {}", newAccountStatus);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account body) {
        return null;
    }

    @Override
    public Account findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.get();
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll().stream().map(account -> account).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    private static <T> void checkIfAlreadyExist(T body) {
        if (body != null) {
            if (body instanceof User)
                throw new CodiceFiscaleException("User associated with Fiscal Code: " + ((User) body).getCodiceFiscale() + " not found.", "002");
        }
    }
}
