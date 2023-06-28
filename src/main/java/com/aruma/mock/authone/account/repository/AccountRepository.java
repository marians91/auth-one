package com.aruma.mock.authone.account.repository;


import com.aruma.mock.authone.account.beans.model.Account;
import com.aruma.mock.authone.account.beans.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUser(User user);
    @Override
    Optional<Account> findById(Long id);

    @Override
    void deleteById(Long id);

}
