package avlyakulov.timur.accounts.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfiguration {
    private final RemoteServiceProperties cardsServiceProperties;

}