package com.aruma.mock.authone.account.beans.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO {
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;

}
