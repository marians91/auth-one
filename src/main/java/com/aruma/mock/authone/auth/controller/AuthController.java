package com.aruma.mock.authone.auth.controller;

import com.aruma.mock.authone.account.beans.dto.AccountDTO;
import com.aruma.mock.authone.account.beans.response.ResponseMessage;
import com.aruma.mock.authone.auth.model.AuthDTO;
import com.aruma.mock.authone.auth.service.AuthService;
import com.aruma.mock.authone.auth.service.AuthSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthSystem authSystem;
    private final AuthService authService;

    public AuthController(AuthSystem authSystem, AuthService authService) {
        this.authSystem = authSystem;
        this.authService = authService;
    }

    @GetMapping("/load/{username}")
    public ResponseEntity<ResponseMessage> loadAccount(@PathVariable("username") String username) {
        logger.debug("Loading account: {}", username);
        ResponseMessage responseMessage = new ResponseMessage();

        AccountDTO accountDTO = authService.loadAccount(username);
        responseMessage.setBody(accountDTO);
        responseMessage.setMessage("Account successfully loaded!");
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseMessage> authenticate(@RequestBody AuthDTO authDTO) {
        logger.debug("Authenticating account: {}", authDTO.getCredentialsDTO().getUsername());
        ResponseMessage responseMessage = new ResponseMessage<>();
        boolean result = this.authSystem.authenticate(authDTO);
        if (result) {
            responseMessage.setMessage("Account successfully authenticated!");
            return ResponseEntity.ok(responseMessage);
        }
        responseMessage.setMessage("Authentication Failed.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }
}
