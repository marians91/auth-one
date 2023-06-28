package com.aruma.mock.authone.account.repository.mapper;

import com.aruma.mock.authone.account.beans.dto.UserDTO;
import com.aruma.mock.authone.account.beans.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends IMapper<User, UserDTO> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
