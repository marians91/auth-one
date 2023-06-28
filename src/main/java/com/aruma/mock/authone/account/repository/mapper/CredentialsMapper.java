package com.aruma.mock.authone.account.repository.mapper;

import com.aruma.mock.authone.account.beans.dto.CredentialsDTO;
import com.aruma.mock.authone.account.beans.model.Credentials;
import com.aruma.mock.authone.core.utils.PasswordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CredentialsMapper extends IMapper<Credentials, CredentialsDTO> {

    CredentialsMapper INSTANCE = Mappers.getMapper(CredentialsMapper.class);

    @Mapping(source = "password", target = "password", qualifiedByName = "encoder")
    Credentials dtoToEntity(CredentialsDTO dto);

    CredentialsDTO entityToDto(Credentials entity);

    @Named("encoder")
    default String passwordEncoder(String password) {
        return PasswordUtils.encodePassword(password);
    }
}