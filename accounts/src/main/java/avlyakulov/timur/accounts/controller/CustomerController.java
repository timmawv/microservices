package avlyakulov.timur.accounts.controller;

import avlyakulov.timur.accounts.dto.CustomerDetailsDto;
import avlyakulov.timur.accounts.service.CustomerServiceI;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceI customerServiceI;

    @GetMapping("/customer-details")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestParam @Valid @Pattern(regexp = "(^$|[0-9]{4})", message = "Mobile number must be 4 digits") String mobileNumber) {
        return ResponseEntity.ok(customerServiceI.getCustomerDetails(mobileNumber));
    }
}
