package avlyakulov.timur.accounts.feign;

import avlyakulov.timur.accounts.dto.external.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeign {

    @Override
    public ResponseEntity<LoansDto> getLoan(String traceId, String mobileNumber) {
        return null;
    }
}
