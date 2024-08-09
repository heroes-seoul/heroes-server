package heroes.domain.auth.application;

import heroes.domain.auth.dto.request.KakaoTokenLoginRequest;
import heroes.domain.auth.dto.response.KakaoTokenLoginResponse;
import heroes.infra.config.oauth.KakaoProperties;
import heroes.infra.feign.KakaoLoginClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoProperties properties;

    public KakaoTokenLoginResponse getIdToken(String code) {
        return kakaoLoginClient.getToken(
                KakaoTokenLoginRequest.newInstance(properties, code).toString());
    }
}
