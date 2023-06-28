package com.aruma.mock.authone.account.beans.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage<T> {
    private T body;
    private String message;

    public ResponseMessage(T body) {
        this.body = body;
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(T body, String message) {
        this.body = body;
        this.message = message;
    }
}
