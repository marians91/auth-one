package com.aruma.mock.authone.account.beans.model;

import com.aruma.mock.authone.core.utils.enums.AccountStatus;
import com.aruma.mock.authone.core.utils.enums.IdEncoderDecoder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "is_enabled")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.DISABLED;
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id",referencedColumnName = "id")
    private Document document;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "codice_fiscale",referencedColumnName = "codice_fiscale",nullable = false)
    User user;

}
