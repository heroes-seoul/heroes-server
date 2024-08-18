package heroes.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
    @Schema(description = "유저 프로필 이미지")
    private String profileUrl;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 이메일")
    private String email;
}
