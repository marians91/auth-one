package com.aruma.mock.authone.account.repository;

import com.aruma.mock.authone.account.beans.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCodiceFiscale(String codiceFiscale);

    @Override
    Optional<User> findById(Long aLong);
}
