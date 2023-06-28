package com.aruma.mock.authone.account.repository;

import com.aruma.mock.authone.account.beans.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Override
    Optional<Document> findById(Long aLong);

    @Override
    void deleteById(Long aLong);
}
