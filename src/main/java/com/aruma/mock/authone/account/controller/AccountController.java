package com.aruma.mock.authone.account.controller;


import com.aruma.mock.authone.account.beans.dto.AccountDTO;
import com.aruma.mock.authone.account.beans.response.ResponseMessage;
import com.aruma.mock.authone.account.service.AccountService;
import com.aruma.mock.authone.core.utils.enums.AccountStatus;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO account) {
        log.info("Received request to create an account");

        ResponseMessage<AccountDTO> response = new ResponseMessage<>();
        AccountDTO createdAccount = accountService.create(account);
        response.setMessage(String.format("Account successfully created. Status: %s", createdAccount.getAccountStatus().name()));

        log.info("Account successfully created. Account ID: {}", createdAccount.getId());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = "/activate/{codiceFiscale}")
    public ResponseEntity<AccountDTO> activate(@RequestParam("file") MultipartFile file, @PathVariable("codiceFiscale") String codiceFiscale) {
        log.info("Received request to activate an account");

        ResponseMessage<AccountDTO> response = new ResponseMessage<>();
        AccountDTO activatedAccount = accountService.activate(file, codiceFiscale);

        if (activatedAccount.getAccountStatus().equals(AccountStatus.ACTIVE)) {
            response.setMessage("Account successfully activated");
        } else {
            response.setMessage("Account not activated.");
        }
        response.setBody(activatedAccount);

        log.info("Account activation completed. Account ID: {}, Status: {}", activatedAccount.getId(), activatedAccount.getAccountStatus().name());

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
