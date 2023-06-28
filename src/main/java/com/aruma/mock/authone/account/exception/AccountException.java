package com.aruma.mock.authone.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountException extends RuntimeException {
    String message;
    String code;
}
