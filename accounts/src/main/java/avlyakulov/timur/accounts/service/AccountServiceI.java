package avlyakulov.timur.accounts.service;

import avlyakulov.timur.accounts.dto.CustomerDto;

public interface AccountServiceI {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - input mobile number
     * @return - returns account details based on a given mobileNumber
     */
    CustomerDto getAccountByMobileNumber(String mobileNumber);
}