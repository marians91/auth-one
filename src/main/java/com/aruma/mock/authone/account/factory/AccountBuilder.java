package com.aruma.mock.authone.account.factory;

import com.aruma.mock.authone.account.beans.dto.CredentialsDTO;
import com.aruma.mock.authone.account.beans.dto.DocumentDTO;
import com.aruma.mock.authone.account.beans.dto.UserDTO;
import com.aruma.mock.authone.account.beans.model.Account;
import com.aruma.mock.authone.account.beans.model.Credentials;
import com.aruma.mock.authone.account.beans.model.Document;
import com.aruma.mock.authone.account.beans.model.User;
import com.aruma.mock.authone.account.extractor.CodiceFiscaleExtractor;
import com.aruma.mock.authone.account.repository.mapper.CredentialsMapper;
import com.aruma.mock.authone.account.repository.mapper.DocumentMapper;
import com.aruma.mock.authone.account.repository.mapper.UserMapper;
import com.aruma.mock.authone.account.service.CredentialsService;
import com.aruma.mock.authone.account.service.DocumentService;
import com.aruma.mock.authone.account.service.UserService;
import com.aruma.mock.authone.core.utils.enums.AccountStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountBuilder {

    private static final Logger logger = LoggerFactory.getLogger(AccountBuilder.class);

    private Account account;
    private final UserService userService;
    private final DocumentService documentService;
    private final CredentialsService credentialsService;

    public AccountBuilder(UserService userService, DocumentService documentService, CredentialsService credentialsService) {
        this.userService = userService;
        this.documentService = documentService;
        this.credentialsService = credentialsService;
    }

    public AccountBuilder initialize() {
        logger.debug("Initializing AccountBuilder");
        this.account = new Account();
        return this;
    }

    public AccountBuilder user(UserDTO userDTO) {
        logger.debug("Building user: {} {}", userDTO.getName(), userDTO.getSurname());
        UserMapper userMapper = UserMapper.INSTANCE;
        User user = userMapper.dtoToEntity(userDTO);
        user.setCredentials(credentials(userDTO.getCredentials()));
        user.setNotificationMode(userDTO.getNotificationMode());
        userService.save(user);
        this.account.setUser(user);
        logger.debug("User built successfully");
        return this;
    }

    public Credentials credentials(CredentialsDTO credentialsDTO) {
        CredentialsMapper mapper = CredentialsMapper.INSTANCE;
        Credentials credentials = mapper.dtoToEntity(credentialsDTO);
        return credentialsService.save(credentials);
    }

    public AccountBuilder document(DocumentDTO documentDTO) {
        if (documentDTO == null) return this;
        DocumentMapper mapper = DocumentMapper.INSTANCE;
        Document document = mapper.dtoToEntity(documentDTO);
        document = documentService.save(document);
        this.account.setDocument(document);
        return this;
    }

    public AccountBuilder activate() {
        boolean flag = false;
        byte[] fileData = this.account.getDocument() != null ? this.account.getDocument().getData() : null;
        if (fileData != null && fileData.length > 0) {
            String codiceFiscale = this.account.getUser().getCodiceFiscale();
            String codiceFiscaleFirmatario = CodiceFiscaleExtractor.extractCodiceFiscaleFromP7M(this.account.getDocument().getData());
            boolean verified = CodiceFiscaleExtractor.verifyCodiceFiscale(codiceFiscaleFirmatario, codiceFiscale);
            AccountStatus accountStatus = verified == true ? AccountStatus.ACTIVE : AccountStatus.DISABLED;
            this.account.setAccountStatus(accountStatus);
        } else {
            this.account.setAccountStatus(AccountStatus.DISABLED);
        }
        return this;
    }

    public Account getInstance() {
        return this.account;
    }
}
