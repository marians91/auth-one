package com.aruma.mock.authone.account.controller;

import com.aruma.mock.authone.account.beans.response.ResponseMessage;
import com.aruma.mock.authone.account.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    private final DocumentService documentService;

    public FileController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Received request to upload a file: {}", file.getOriginalFilename());

        try {
            documentService.store(file);
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();

            log.info(message);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (IOException e) {
            log.error("Error occurred while uploading the file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
