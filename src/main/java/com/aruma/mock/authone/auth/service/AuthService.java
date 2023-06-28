package com.aruma.mock.authone.auth.service;

import com.aruma.mock.authone.account.beans.dto.AccountDTO;
import com.aruma.mock.authone.account.beans.model.Account;
import com.aruma.mock.authone.account.beans.model.Credentials;
import com.aruma.mock.authone.account.beans.model.User;
import com.aruma.mock.authone.account.exception.AccountException;
import com.aruma.mock.authone.account.repository.AccountRepository;
import com.aruma.mock.authone.account.repository.mapper.AccountMapper;
import com.aruma.mock.authone.account.service.CredentialsService;
import com.aruma.mock.authone.auth.model.AuthDTO;
import com.aruma.mock.authone.core.utils.PasswordUtils;
import com.aruma.mock.authone.core.utils.enums.IdEncoderDecoder;
import com.aruma.mock.authone.core.utils.enums.NotificationMode;
import com.aruma.mock.authone.notification.entity.AccessInfo;
import com.aruma.mock.authone.notification.entity.NotificationContext;
import com.aruma.mock.authone.notification.factory.NotificationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    AccountMapper mapper = AccountMapper.INSTANCE;
    private final CredentialsService credentialsService;
    private final AccountRepository accountRepository;

    public AuthService(CredentialsService credentialsService, AccountRepository accountRepository) {
        this.credentialsService = credentialsService;
        this.accountRepository = accountRepository;
    }

    public AccountDTO loadAccount(String username) {
        try {
            User user = credentialsService.findByUsername(username).getUserc();
            if (user == null) throw new AccountException("Invalid username.", "009");
            Account account = this.accountRepository.findByUser(user);
            AccountDTO accountDTO = mapper.entityToDto(account);
            return accountDTO;
        } catch (Exception e) {
            logger.error("Failed to load account: {}", username, e);
            throw e;
        }
    }

    public boolean authenticateAccount(AuthDTO authDTO) {
        try {
            Account account = getAccountInfo(authDTO);
            String inputPassword = authDTO.getCredentialsDTO().getPassword();

            User user = account.getUser();
            NotificationMode notificationMode = user.getNotificationMode();
            String encodedPassword = user.getCredentials().getPassword();

            boolean isAuthenticated = validatePassword(inputPassword, encodedPassword);
            sendNotification(notificationMode);
            return isAuthenticated;
        } catch (Exception e) {
            logger.error("Failed to authenticate account: {}", authDTO.getCredentialsDTO().getUsername(), e);
            throw e;
        }
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return PasswordUtils.verifyPassword(password, encodedPassword);
    }

    private void sendNotification(NotificationMode notificationMode) {
        NotificationContext notificationContext = new NotificationContext(NotificationFactory.supplyStrategy(notificationMode));
        notificationContext.executeStrategy(new AccessInfo());
    }

    private Account getAccountInfo(AuthDTO authDTO) {
       // Long accountId = IdEncoderDecoder.decodeId(authDTO.getAccountId());
        Long accountId = authDTO.getAccountId();
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountException("Account not found", "010"));
        return account;
    }
}
