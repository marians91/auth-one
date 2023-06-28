package com.aruma.mock.authone.auth.service;

import com.aruma.mock.authone.account.service.UserService;
import com.aruma.mock.authone.auth.model.AuthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.aruma.mock.authone.core.utils.Constants.Factor.ONE_FACTOR;
import static com.aruma.mock.authone.core.utils.Constants.Factor.TWO_FACTOR;

@Slf4j
@Component
public class AuthSystem {
    private final UserService userService;
    private final AuthService authService;

    public AuthSystem(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    public boolean authenticate(AuthDTO auth) {
        final String authMode = auth.getMode();
        try {
            switch (authMode) {
                case ONE_FACTOR:
                    return this.authService.authenticateAccount(auth);
                case TWO_FACTOR:
                    throw new IllegalArgumentException("Operation not supported.");
                default:
                    throw new IllegalArgumentException("Authentication mode is missing.");
            }
        } catch (Exception e) {
            log.error("Failed to authenticate: {}", auth, e);
            throw e;
        }
    }

}
