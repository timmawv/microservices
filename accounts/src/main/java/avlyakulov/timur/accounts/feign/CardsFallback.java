package avlyakulov.timur.accounts.feign;

import avlyakulov.timur.accounts.dto.external.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeign {

    @Override
    public ResponseEntity<CardsDto> getCard(String traceId, String mobileNumber) {
        return null;
    }
}
