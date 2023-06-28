package com.aruma.mock.authone.auth.model;

import com.aruma.mock.authone.account.beans.dto.CredentialsDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    private Long accountId;
    private String mode;
    private CredentialsDTO credentialsDTO;
}
