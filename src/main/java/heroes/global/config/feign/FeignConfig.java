package heroes.global.config.feign;

import feign.RequestInterceptor;
import java.util.Collections;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "heroes.infra.feign")
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String contentType =
                    template
                            .headers()
                            .getOrDefault("Content-Type", Collections.emptyList())
                            .stream()
                            .findFirst()
                            .orElse("");
            if (contentType.equals("application/json")) {
                template.header("Content-Type", "application/json;charset=utf-8");
            } else {
                template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            }
        };
    }
}
