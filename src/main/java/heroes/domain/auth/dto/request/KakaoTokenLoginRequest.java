package heroes.domain.auth.dto.request;

import heroes.infra.config.oauth.KakaoProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KakaoTokenLoginRequest {

    private String code;
    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String grantType;

    public static KakaoTokenLoginRequest newNormalRequest(KakaoProperties properties, String code) {
        return KakaoTokenLoginRequest.builder()
                .code(code)
                .clientId(properties.getId())
                .clientSecret(properties.getSecret())
                .redirectUri(properties.getNormalRedirectUri())
                .grantType(properties.getGrantType())
                .build();
    }

    public static KakaoTokenLoginRequest newCompanyRequest(
            KakaoProperties properties, String code) {
        return KakaoTokenLoginRequest.builder()
                .code(code)
                .clientId(properties.getId())
                .clientSecret(properties.getSecret())
                .redirectUri(properties.getCompanyRedirectUri())
                .grantType(properties.getGrantType())
                .build();
    }

    @Override
    public String toString() {
        return "grant_type="
                + grantType
                + "&client_id="
                + clientId
                + "&redirect_uri="
                + redirectUri
                + "&code="
                + code
                + "&client_secret="
                + clientSecret;
    }
}
