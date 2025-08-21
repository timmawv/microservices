package avlyakulov.timur.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	//способ как можно переопределить routing в gateway
	@Bean
	public RouteLocator BankRoutConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(path -> path
						.path("/bank/accounts/**")
						.filters(filter -> filter.rewritePath("/bank/accounts/(?<segment>.*)", "/${segment}")) //мы форвардим наш запрос по такому пути
						.uri("lb://ACCOUNTS"))  //lb - load balancer
				.build();
	}
}
