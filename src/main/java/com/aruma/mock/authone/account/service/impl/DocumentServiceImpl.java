package com.aruma.mock.authone.account.service.impl;

import com.aruma.mock.authone.account.beans.model.Document;
import com.aruma.mock.authone.account.repository.DocumentRepository;
import com.aruma.mock.authone.account.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Document store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Document document = Document.builder().name(fileName).data(file.getBytes()).type(file.getContentType()).build();

        logger.debug("Storing document: {}", fileName);
        return save(document);
    }

    @Override
    public Document store(byte[] file, String fileName, String contentType) throws IOException {
        Document document = Document.builder().name(fileName).data(file).type(contentType).build();
        logger.debug("Storing document: {}", fileName);
        return save(document);
    }

    @Override
    public Document save(Document body) {
        logger.debug("Saving document: {}", body.getName());
        return documentRepository.save(body);
    }

    @Override
    public Document findById(Long id) {
        logger.debug("Finding document by ID: {}", id);
        return documentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Document> findAll() {
        logger.debug("Finding all documents");
        return documentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("Deleting document by ID: {}", id);
        documentRepository.deleteById(id);
    }

    @Override
    public Document update(Document body) {
        return null;
    }
}
