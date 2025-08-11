package avlyakulov.timur.accounts.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "sys-opt.api.cards")
    public RemoteServiceProperties cardsServiceProperties() {
        return new RemoteServiceProperties();
    }
}