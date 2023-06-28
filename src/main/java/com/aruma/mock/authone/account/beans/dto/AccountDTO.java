package com.aruma.mock.authone.account.beans.dto;

import com.aruma.mock.authone.core.utils.enums.AccountStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class AccountDTO {
    private AccountStatus accountStatus;
    private Long id;
    private DocumentDTO document;
    @Valid
    @NotNull
    private UserDTO user;

}
