package com.aruma.mock.authone.account.repository.mapper;

public interface IMapper<Entity, DTO> {

    Entity dtoToEntity(DTO dto);

    DTO entityToDto(Entity entity);

}
