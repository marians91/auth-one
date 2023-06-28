package com.aruma.mock.authone.account.controller.support;

import com.aruma.mock.authone.account.beans.response.ErrorResponse;
import com.aruma.mock.authone.account.exception.AccountException;
import com.aruma.mock.authone.account.exception.CodiceFiscaleException;
import com.aruma.mock.authone.account.exception.FileException;
import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerAdviceHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdviceHandler.class);

    @ResponseBody
    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorResponse> handleFileException(HttpServletRequest request,
                                                             FileException e) {

        log.error("FileException occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(CodiceFiscaleException.class)
    public ResponseEntity<ErrorResponse> handleCodiceFiscaleException(HttpServletRequest request,
                                                                      CodiceFiscaleException e) {

        log.error("CodiceFiscaleException occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorResponse> handleAccountException(HttpServletRequest request,
                                                                AccountException e) {

        log.error("AccountException occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest request,
                                                                        IllegalArgumentException e) {

        log.error("IllegalArgumentException occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorResponse.setMessage(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request,
                                                         MethodArgumentNotValidException e) {

        log.error("MethodArgumentNotValidException occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorResponse.setMessage(e.getMessage());
        log.error(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request,
                                                         Exception e) {

        log.error("IllegalArgumentException occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        errorResponse.setMessage("Something went wrong.");
        log.error(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
