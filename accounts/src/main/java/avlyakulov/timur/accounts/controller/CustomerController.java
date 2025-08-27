package avlyakulov.timur.accounts.controller;

import avlyakulov.timur.accounts.dto.CustomerDetailsDto;
import avlyakulov.timur.accounts.service.CustomerServiceI;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceI customerServiceI;

    @GetMapping("/customer-details")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(
            @RequestHeader("bank-trace-id") String traceId,
            @RequestParam @Valid @Pattern(regexp = "(^$|[0-9]{4})", message = "Mobile number must be 4 digits") String mobileNumber) {
        log.debug("bank-trace-id found: {}", traceId);
        return ResponseEntity.ok(customerServiceI.getCustomerDetails(mobileNumber, traceId));
    }
}
