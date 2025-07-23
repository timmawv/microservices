package avlyakulov.timur.accounts;

import avlyakulov.timur.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "Bank Accounts microservice",
                version = "v1",
                contact = @Contact(
                        name = "Tymur Avliakulov",
                        email = "timur@gmail.com",
                        url = "https://github.com/timmawv"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/timmawv"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Bank Accounts microservice REST API Documentation",
                url = "https://github.com/timmawv"
        )
)//это просто описание нашего сервиса в swagger
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }
}