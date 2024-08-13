package heroes.global.config.s3;

import static software.amazon.awssdk.regions.Region.AP_NORTHEAST_2;

import heroes.infra.config.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private final StorageProperties storageProperties;

    @Bean
    public S3Presigner presigner() {
        AwsBasicCredentials awsCreds =
                AwsBasicCredentials.create(
                        storageProperties.getAccessKey(), storageProperties.getSecretKey());
        return S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(AP_NORTHEAST_2)
                .build();
    }
}
