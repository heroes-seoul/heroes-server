package heroes.domain.member.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class OauthInfo {
    private String oauthId;
    private String oauthEmail;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;
}
