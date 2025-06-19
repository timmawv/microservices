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

    /**
     *
     * @param customerDto
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber
     * @return boolean indicating if the account was deleted successful or not
     */
    boolean deleteAccount(String mobileNumber);
}