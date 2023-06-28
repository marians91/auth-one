package com.aruma.mock.authone.account.beans.dto;


import com.aruma.mock.authone.core.utils.enums.NotificationMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Valid
public class UserDTO {
    private Long id;
    @NotNull
    @Pattern(regexp = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$")
    private String codiceFiscale;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Valid
    @NotNull
    private CredentialsDTO credentials;
    private NotificationMode notificationMode = NotificationMode.EMAIL;
}