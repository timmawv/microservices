package avlyakulov.timur.accounts.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteServiceProperties {
    private String url;
    private Integer readTimeout;
    private Integer connectionTimeout;
    private Integer retryAttemptCount;
    private Integer secondsBetweenAttempts;
}