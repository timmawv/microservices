package avlyakulov.timur.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	//todo вынести это в отдельный конфиг файл
	//способ как можно переопределить routing в gateway
	@Bean
	public RouteLocator BankRoutConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(path -> path
						.path("/bank/accounts/**")
						//мы форвардим наш запрос по такому пути
						.filters(filter -> filter.rewritePath("/bank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://ACCOUNTS"))  //lb - load balancer
				.route(path -> path
						.path("/bank/loans/**")
						.filters(filter -> filter.rewritePath("/bank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3) //сколько раз будет пытаться ретраить
										.setMethods(HttpMethod.GET) //на каком http методе будет это вызываться
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						//в это схеме 1 параметре - через сколько будет сделана первая попытка, то есть после 100 мс после неудачи
						//2 параметр - максимальное время через которое будет вызываться ретрай попытка
						//3 параметр - это реплика фактор, то есть умножение на предыдущие, 1 попытка через 100 мс, 2 попытка через 200мс, но самая последняя не больше 1000 мс
						//4 параметр - основываться ли на предыдущем значении
						.uri("lb://LOANS"))
				.route(path -> path
						.path("/bank/cards/**")
						.filters(filter -> filter.rewritePath("/bank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
//								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()) //настройка rateLimiter
//										.setKeyResolver(userKeyResolver()))
						)
						.uri("lb://CARDS"))
				.build();
	}

	//Способ увеличить максимальное время ожидание выполнения запроса
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

//	@Bean
//	public RedisRateLimiter redisRateLimiter() {
//		return new RedisRateLimiter(1, 1, 1);
//	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}
}