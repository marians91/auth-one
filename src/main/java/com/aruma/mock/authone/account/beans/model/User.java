package com.aruma.mock.authone.account.beans.model;

import com.aruma.mock.authone.core.utils.enums.NotificationMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Credentials credentials;
    @OneToOne(mappedBy = "user")
    private Account account;
    @Column(name = "notificationMode")
    @Enumerated(EnumType.STRING)
    private NotificationMode notificationMode = NotificationMode.EMAIL;

    public void setNotificationMode(NotificationMode notificationMode) {
        if (notificationMode == null) this.notificationMode = NotificationMode.EMAIL;
        else this.notificationMode = notificationMode;
    }
}
