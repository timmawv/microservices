package avlyakulov.timur.accounts.feign;

import avlyakulov.timur.accounts.dto.external.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeign {

    @GetMapping(value = "/api/loan", consumes = "application/json")
    ResponseEntity<LoansDto> getLoan(@RequestHeader("bank-trace-id") String traceId, @RequestParam String mobileNumber);
}
