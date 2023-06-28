package com.aruma.mock.authone.account.repository;

import com.aruma.mock.authone.account.beans.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    boolean existsByUsername(String username);
    Credentials findByUsername(String username);
    @Override
    Optional<Credentials> findById(Long aLong);

    @Override
    void deleteById(Long aLong);
}
