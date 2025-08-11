package avlyakulov.timur.accounts.feign;

import avlyakulov.timur.accounts.dto.external.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeign {

    @GetMapping(value = "/api/card", consumes = "application/json")
    ResponseEntity<CardsDto> getCard(@RequestParam String mobileNumber);
}