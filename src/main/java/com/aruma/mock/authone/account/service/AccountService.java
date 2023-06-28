package com.aruma.mock.authone.account.service;

import com.aruma.mock.authone.account.beans.dto.AccountDTO;
import com.aruma.mock.authone.account.beans.model.Account;
import com.aruma.mock.authone.core.utils.enums.AccountStatus;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService extends CrudOperations<Account> {
    AccountDTO create(AccountDTO account);

    AccountDTO activate(MultipartFile file, String codiceFiscale);

    void updateAccountStatus(Account account, AccountStatus newAccountStatus);


}
