package avlyakulov.timur.accounts.service.impl;

import avlyakulov.timur.accounts.dto.AccountsDto;
import avlyakulov.timur.accounts.dto.CustomerDto;
import avlyakulov.timur.accounts.entity.Accounts;
import avlyakulov.timur.accounts.entity.Customer;
import avlyakulov.timur.accounts.exception.CustomerAlreadyExistsException;
import avlyakulov.timur.accounts.exception.ResourceNotFoundException;
import avlyakulov.timur.accounts.mapper.AccountsMapper;
import avlyakulov.timur.accounts.mapper.CustomerMapper;
import avlyakulov.timur.accounts.repository.AccountsRepository;
import avlyakulov.timur.accounts.repository.CustomerRepository;
import avlyakulov.timur.accounts.service.AccountServiceI;
import avlyakulov.timur.accounts.util.AccountsConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountServiceI {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    @Setter(onMethod_ = @__({@Autowired}))
    private Validator validator;

    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        validateCustomerDtoInDifferentWay(customerDto);
        Customer customer = customerMapper.mapToEntity(customerDto);
        customerRepository.findByMobileNumberAndIsDeletedIsFalse(customer.getMobileNumber())
                .ifPresent(existingCustomer -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                            + customer.getMobileNumber());
                });
        customer.setIsDeleted(false);
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccounts(savedCustomer));
    }

    @Override
    public CustomerDto getAccountByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndIsDeletedIsFalse(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        return customerMapper.mapToDto(customer, accounts);
    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));
            updateAccount(accounts, accountsDto);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));
            updateCustomer(customer, customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndIsDeletedIsFalse(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        customer.setIsDeleted(true);
        accounts.setIsDeleted(true);
        return true;
    }

    private Accounts createNewAccounts(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(9000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    private void updateAccount(Accounts accounts, AccountsDto accountsDto) {
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
    }

    private void updateCustomer(Customer customer, CustomerDto customerDto) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customer.getMobileNumber());
    }

    private void validateCustomerDtoInDifferentWay(CustomerDto customerDto) {
        var violations = validator.validate(customerDto);
        if (violations.isEmpty()) {
            log.info("No errors");
        } else {
            log.info("Error is here: ");
            System.out.println(violations);
            throw new RuntimeException();
        }
    }
}