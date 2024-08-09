package heroes.domain.auth.dto;

import heroes.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessTokenDto {
    private Long memberId;
    private MemberRole memberRole;
    private String token;
}
