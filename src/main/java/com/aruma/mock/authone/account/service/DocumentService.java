package com.aruma.mock.authone.account.service;

import com.aruma.mock.authone.account.beans.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface DocumentService extends CrudOperations<Document> {
    Document store(MultipartFile file) throws IOException;

    Document store(byte[] file, String fileName, String contentType) throws IOException;

}
