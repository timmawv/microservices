package avlyakulov.timur.accounts.service.impl;

import avlyakulov.timur.accounts.dto.CustomerDetailsDto;
import avlyakulov.timur.accounts.dto.external.CardsDto;
import avlyakulov.timur.accounts.dto.external.LoansDto;
import avlyakulov.timur.accounts.entity.Accounts;
import avlyakulov.timur.accounts.entity.Customer;
import avlyakulov.timur.accounts.exception.ResourceNotFoundException;
import avlyakulov.timur.accounts.feign.CardsFeign;
import avlyakulov.timur.accounts.feign.LoansFeign;
import avlyakulov.timur.accounts.mapper.AccountsMapper;
import avlyakulov.timur.accounts.mapper.CustomerMapper;
import avlyakulov.timur.accounts.repository.AccountsRepository;
import avlyakulov.timur.accounts.repository.CustomerRepository;
import avlyakulov.timur.accounts.service.CustomerServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceI {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeign cardsFeign;
    private final LoansFeign loansFeign;
    private final CustomerMapper customerMapper;
    private final AccountsMapper accountsMapper;

    public CustomerDetailsDto getCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndIsDeletedIsFalse(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDetailsDto customerDetailsDto = customerMapper.mapToCustomerDetailsDto(customer);
        customerDetailsDto.setAccountsDto(accountsMapper.mapToDto(accounts));
        ResponseEntity<LoansDto> loan = loansFeign.getLoan(mobileNumber);
        ResponseEntity<CardsDto> card = cardsFeign.getCard(mobileNumber);
        customerDetailsDto.setCardsDto(card.getBody());
        customerDetailsDto.setLoansDto(loan.getBody());
        return customerDetailsDto;
    }
}