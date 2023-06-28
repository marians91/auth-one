package com.aruma.mock.authone.account.service.impl;

import com.aruma.mock.authone.account.beans.model.Credentials;
import com.aruma.mock.authone.account.exception.AccountException;
import com.aruma.mock.authone.account.repository.CredentialsRepository;
import com.aruma.mock.authone.account.service.CredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CredentialsServiceImpl implements CredentialsService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialsServiceImpl.class);

    private final CredentialsRepository credentialsRepository;

    public CredentialsServiceImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public Credentials save(Credentials body) {
        logger.debug("Saving credentials");
        return credentialsRepository.save(body);
    }

    @Override
    public Credentials update(Credentials body) {
        return null;
    }

    @Override
    public Credentials findById(Long id) {
        Optional<Credentials> credentials = credentialsRepository.findById(id);
        return credentials.get();
    }

    public Credentials findByUsername(String username) {
        logger.debug("Finding credentials by username: {}", username);
        Optional<Credentials> credentials = Optional.ofNullable(credentialsRepository.findByUsername(username));
        return credentials.orElseThrow(() -> new AccountException("Username is not associated with any account.", "008"));
    }

    public Boolean checkIfExist(String username) {
        logger.debug("Checking if credentials exist by username: {}", username);
        Boolean exist = credentialsRepository.existsByUsername(username);
        return exist;
    }

    @Override
    public List<Credentials> findAll() {
        logger.debug("Finding all credentials");
        return credentialsRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        credentialsRepository.deleteById(id);
    }
}
