package heroes.infra.config.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String cloudfront;
}
