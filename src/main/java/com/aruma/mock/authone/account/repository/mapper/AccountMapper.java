package com.aruma.mock.authone.account.repository.mapper;

import com.aruma.mock.authone.account.beans.dto.AccountDTO;
import com.aruma.mock.authone.account.beans.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper extends IMapper<Account, AccountDTO> {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

   // @Mapping(source = "id", target = "id", qualifiedByName = "idEncoder")
    AccountDTO entityToDto(Account entity);

    /**@Named("idEncoder") default String idEncoder(Long id) {
    return IdEncoderDecoder.encodeId(id);
    }**/

}