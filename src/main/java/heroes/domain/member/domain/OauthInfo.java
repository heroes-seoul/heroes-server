package heroes.domain.member.domain;

import static heroes.domain.member.domain.OauthProvider.KAKAO;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Embeddable
@Getter
@NoArgsConstructor
public class OauthInfo {
    private String oauthId;
    private String oauthEmail;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    @Builder
    public OauthInfo(String oauthId, String oauthEmail, OauthProvider provider) {
        this.oauthId = oauthId;
        this.oauthEmail = oauthEmail;
        this.provider = provider;
    }

    public static OauthInfo from(OidcUser user) {
        return OauthInfo.builder()
                .oauthId(user.getSubject())
                .oauthEmail(user.getEmail())
                .provider(KAKAO)
                .build();
    }
}
