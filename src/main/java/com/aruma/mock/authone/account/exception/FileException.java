package com.aruma.mock.authone.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileException extends RuntimeException {
    String message;
    String code;
}
