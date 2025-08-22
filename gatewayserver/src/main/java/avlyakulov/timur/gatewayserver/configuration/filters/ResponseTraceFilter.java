package avlyakulov.timur.gatewayserver.configuration.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class ResponseTraceFilter {

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String traceId = FilterUtils.getTraceId(requestHeaders);
                log.debug("Updated the trace id to the outbound headers: {}", traceId);
                exchange.getResponse().getHeaders().add(FilterUtils.TRACE_ID, traceId);
            }));
        };
    }
}