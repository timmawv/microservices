package avlyakulov.timur.accounts.service;

import avlyakulov.timur.accounts.dto.CustomerDto;

public interface AccountServiceI {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);
}