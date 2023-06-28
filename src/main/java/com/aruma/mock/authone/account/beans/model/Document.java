package com.aruma.mock.authone.account.beans.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "document")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    private String type;
    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;
    @OneToOne(mappedBy = "document")
    private Account account;

}
