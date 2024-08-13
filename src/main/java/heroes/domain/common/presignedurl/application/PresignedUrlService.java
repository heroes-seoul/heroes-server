package heroes.domain.common.presignedurl.application;

import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.infra.config.storage.StorageProperties;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class PresignedUrlService {
    private final S3Presigner presigner;
    private final StorageProperties storageProperties;

    public PresignedUrlIssueResponse generatePresignedUrl(String objectKey) {
        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(storageProperties.getBucket())
                        .key(objectKey)
                        .build();

        PutObjectPresignRequest getObjectPresignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(5))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedPutObjectRequest =
                presigner.presignPutObject(getObjectPresignRequest);

        String url = presignedPutObjectRequest.url().toString();

        presigner.close();

        return new PresignedUrlIssueResponse(url);
    }
}
