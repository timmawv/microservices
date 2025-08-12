package avlyakulov.timur.accounts.configuration;

import feign.Client;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class OkHttpClientConfiguration {

    public static final String DEFAULT_OK_HTTP_CLIENT_BEAN  = "DefaultSslOkHttpClient";

    @Bean(name = DEFAULT_OK_HTTP_CLIENT_BEAN)
    public Client feignSllClient(@Qualifier("okhttp30HttpClient") OkHttpClient client) {
        return new feign.okhttp.OkHttpClient(client.newBuilder().build());
    }
}
