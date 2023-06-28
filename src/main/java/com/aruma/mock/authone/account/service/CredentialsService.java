package com.aruma.mock.authone.account.service;

import com.aruma.mock.authone.account.beans.model.Credentials;

public interface CredentialsService extends CrudOperations<Credentials> {
    public Credentials findByUsername(String username);

    public Boolean checkIfExist(String username);
}
