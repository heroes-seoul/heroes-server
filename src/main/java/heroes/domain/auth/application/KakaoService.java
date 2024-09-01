package heroes.domain.auth.application;

import static heroes.global.common.constants.SecurityConstants.KAKAO_WITHDRAWAL_HEADER_PREFIX;
import static heroes.global.common.constants.SecurityConstants.KAKAO_WITHDRAWAL_TYPE;

import heroes.domain.auth.dto.request.KakaoTokenLoginRequest;
import heroes.domain.auth.dto.response.KakaoTokenLoginResponse;
import heroes.infra.config.oauth.KakaoProperties;
import heroes.infra.feign.KakaoLoginClient;
import heroes.infra.feign.KakaoWithdrawalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoWithdrawalClient kakaoWithdrawalClient;
    private final KakaoProperties properties;

    public KakaoTokenLoginResponse getIdToken(String code) {
        return kakaoLoginClient.getToken(
                KakaoTokenLoginRequest.newInstance(properties, code).toString());
    }

    public void withdrawal(Long oauthId) {
        kakaoWithdrawalClient.withdrawal(
                KAKAO_WITHDRAWAL_HEADER_PREFIX + properties.getAdmin(),
                KAKAO_WITHDRAWAL_TYPE,
                oauthId);
    }
}
