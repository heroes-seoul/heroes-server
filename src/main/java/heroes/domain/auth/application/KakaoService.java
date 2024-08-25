package heroes.domain.auth.application;

import static heroes.global.common.constants.SecurityConstants.KAKAO_WITHDRAWAL_HEADER_PREFIX;
import static heroes.global.common.constants.SecurityConstants.KAKAO_WITHDRAWAL_TYPE;

import heroes.domain.auth.dto.request.KakaoTokenLoginRequest;
import heroes.domain.auth.dto.response.KakaoTokenLoginResponse;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.infra.config.oauth.KakaoProperties;
import heroes.infra.feign.KakaoLoginClient;
import heroes.infra.feign.KakaoWithdrawalClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoWithdrawalClient kakaoWithdrawalClient;
    private final KakaoProperties properties;

    public KakaoTokenLoginResponse getNormalUserIdToken(String code) {
        try {
            return kakaoLoginClient.getToken(
                    KakaoTokenLoginRequest.newNormalRequest(properties, code).toString());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.BAD_KAKAO_LOGIN_REQUEST);
        }
    }

    public KakaoTokenLoginResponse getCompanyUserIdToken(String code) {
        try {
            return kakaoLoginClient.getToken(
                    KakaoTokenLoginRequest.newCompanyRequest(properties, code).toString());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.BAD_KAKAO_LOGIN_REQUEST);
        }
    }

    public void withdrawal(Long oauthId) {
        kakaoWithdrawalClient.withdrawal(
                KAKAO_WITHDRAWAL_HEADER_PREFIX + properties.getAdmin(),
                KAKAO_WITHDRAWAL_TYPE,
                oauthId);
    }
}
