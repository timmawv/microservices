package avlyakulov.timur.accounts.mapper;

import avlyakulov.timur.accounts.dto.AccountsDto;
import avlyakulov.timur.accounts.entity.Accounts;
import org.mapstruct.Mapper;

@Mapper
public interface AccountsMapper {

    AccountsDto mapToDto(Accounts accounts);

    Accounts mapToEntity(AccountsDto accountsDto);
}