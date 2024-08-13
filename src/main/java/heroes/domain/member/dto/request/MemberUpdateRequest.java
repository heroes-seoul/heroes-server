package heroes.domain.member.dto.request;

import heroes.domain.member.domain.District;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String nickname;
    private District district;
}
