package com.aruma.mock.authone.account.beans.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDTO {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    private byte[] data;
}
