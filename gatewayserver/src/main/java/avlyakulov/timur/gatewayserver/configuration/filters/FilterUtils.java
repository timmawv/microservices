package avlyakulov.timur.gatewayserver.configuration.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public class FilterUtils {

    public static final String TRACE_ID = "bank-trace-id";

    public static String getTraceId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(TRACE_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(TRACE_ID);
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public static ServerWebExchange setTraceId(ServerWebExchange exchange, String traceId) {
        return setRequestHeader(exchange, TRACE_ID, traceId);
    }
}
