package avlyakulov.timur.accounts.service.impl;

import avlyakulov.timur.accounts.dto.CustomerDto;
import avlyakulov.timur.accounts.entity.Accounts;
import avlyakulov.timur.accounts.entity.Customer;
import avlyakulov.timur.accounts.exception.CustomerAlreadyExistsException;
import avlyakulov.timur.accounts.mapper.CustomerMapper;
import avlyakulov.timur.accounts.repository.AccountsRepository;
import avlyakulov.timur.accounts.repository.CustomerRepository;
import avlyakulov.timur.accounts.service.AccountServiceI;
import avlyakulov.timur.accounts.util.AccountsConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountServiceI {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.mapToEntity(customerDto);
        customerRepository.findByMobileNumber(customer.getMobileNumber())
                .ifPresent(existingCustomer -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                            + customer.getMobileNumber());
                });
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        //accountsRepository.save(createNewAccounts(savedCustomer));
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
}