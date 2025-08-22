package avlyakulov.timur.gatewayserver.configuration.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isTraceIdPresent(requestHeaders)) {
            log.debug("bank-trace-id found in RequestTraceFilter : {}",
                    FilterUtils.getTraceId(requestHeaders));
        } else {
            String traceID = generateTraceId();
            exchange = FilterUtils.setTraceId(exchange, traceID);
            log.debug("bank-trace-id generated in RequestTraceFilter : {}", traceID);
        }
        return chain.filter(exchange);
    }

    private boolean isTraceIdPresent(HttpHeaders requestHeaders) {
        if (FilterUtils.getTraceId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String generateTraceId() {
        return java.util.UUID.randomUUID().toString();
    }
}
