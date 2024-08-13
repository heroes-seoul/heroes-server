package heroes.domain.member.dto.request;

import heroes.domain.member.domain.District;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    @Schema(description = "갱신할 닉네임")
    private String nickname;

    @Schema(description = "갱신할 위치 구역")
    private District district;
}
