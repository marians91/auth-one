package com.aruma.mock.authone.account.service;

import com.aruma.mock.authone.account.beans.model.User;

public interface UserService extends CrudOperations<User> {
    User findByCf(String cf);

}
