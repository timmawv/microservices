package avlyakulov.timur.accounts.mapper;

import avlyakulov.timur.accounts.dto.CustomerDto;
import avlyakulov.timur.accounts.entity.Accounts;
import avlyakulov.timur.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

    CustomerDto mapToDto(Customer customer);

    @Mapping(source = "accounts", target = "accountsDto")
    CustomerDto mapToDto(Customer customer, Accounts accounts);

    Customer mapToEntity(CustomerDto customerDto);
}