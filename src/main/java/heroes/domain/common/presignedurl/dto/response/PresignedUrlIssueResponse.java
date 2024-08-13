package heroes.domain.common.presignedurl.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlIssueResponse {
    @Schema(description = "프로필 업로드를 위한 presigned url")
    private String url;
}
