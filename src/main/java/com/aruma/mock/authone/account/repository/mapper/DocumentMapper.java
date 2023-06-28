package com.aruma.mock.authone.account.repository.mapper;

import com.aruma.mock.authone.account.beans.dto.DocumentDTO;
import com.aruma.mock.authone.account.beans.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface DocumentMapper extends IMapper<Document, DocumentDTO> {

    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

}
