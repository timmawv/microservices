package avlyakulov.timur.accounts.mapper;

import avlyakulov.timur.accounts.dto.CustomerDto;
import avlyakulov.timur.accounts.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto mapToDto(Customer customer);

    Customer mapToEntity(CustomerDto customerDto);
}