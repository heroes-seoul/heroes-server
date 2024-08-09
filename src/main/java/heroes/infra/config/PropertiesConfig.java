package heroes.infra.config;

import heroes.infra.config.jwt.JwtProperties;
import heroes.infra.config.oauth.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({KakaoProperties.class, JwtProperties.class})
@Configuration
public class PropertiesConfig {}
