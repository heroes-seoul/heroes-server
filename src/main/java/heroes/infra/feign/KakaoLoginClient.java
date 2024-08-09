package heroes.infra.feign;

import static heroes.global.common.constants.SecurityConstants.KAKAO_LOGIN_ENDPOINT;
import static heroes.global.common.constants.SecurityConstants.KAKAO_LOGIN_URL;

import heroes.domain.auth.dto.response.KakaoTokenLoginResponse;
import heroes.global.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoLoginClient", url = KAKAO_LOGIN_URL, configuration = FeignConfig.class)
public interface KakaoLoginClient {
    @PostMapping(value = KAKAO_LOGIN_ENDPOINT)
    KakaoTokenLoginResponse getToken(@RequestBody String KakaoTokenRequest);
}
