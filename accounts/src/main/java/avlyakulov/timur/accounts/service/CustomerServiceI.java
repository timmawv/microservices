package avlyakulov.timur.accounts.service;

import avlyakulov.timur.accounts.dto.CustomerDetailsDto;

public interface CustomerServiceI {

    /**
     *
     * @param mobileNumber - input mobile number
     * @return - returns account details based on a given mobileNumber
     */
    CustomerDetailsDto getCustomerDetails(String mobileNumber, String traceId);
}
