package heroes.domain.common.presignedurl.application;

import static heroes.global.common.constants.PresignedUrlConstants.BUCKET_DETAIL;
import static heroes.global.common.constants.PresignedUrlConstants.SLASH;

import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.infra.config.storage.StorageProperties;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    public List<PresignedUrlIssueResponse> generatePresignedUrls(
            String objectKeyPrefix, int count) {
        List<PresignedUrlIssueResponse> urls = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String objectKey = objectKeyPrefix + SLASH + UUID.randomUUID();
            urls.add(generatePresignedUrl(objectKey));
        }
        return urls;
    }

    public String convertToCloudfrontUrl(String s3Url) {
        String bucketName = storageProperties.getBucket() + BUCKET_DETAIL;
        String s3Path = s3Url.substring(s3Url.indexOf(bucketName) + bucketName.length());
        return storageProperties.getCloudfront() + s3Path;
    }

    public String getCloudfrontUrl() {
        return storageProperties.getCloudfront();
    }
}
