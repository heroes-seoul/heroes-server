package heroes.infra.config;

import heroes.infra.config.jwt.JwtProperties;
import heroes.infra.config.oauth.KakaoProperties;
import heroes.infra.config.storage.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    KakaoProperties.class,
    JwtProperties.class,
    StorageProperties.class
})
@Configuration
public class PropertiesConfig {}
