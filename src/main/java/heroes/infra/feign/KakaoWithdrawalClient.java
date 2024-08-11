package heroes.infra.feign;

import static heroes.global.common.constants.SecurityConstants.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoWithdrawalClient", url = KAKAO_WITHDRAWAL_URL)
public interface KakaoWithdrawalClient {
    @PostMapping(value = KAKAO_WITHDRAWAL_ENDPOINT, consumes = KAKAO_WITHDRAWAL_URL_ENCODING)
    String withdrawal(
            @RequestHeader(AUTHORIZATION) String authorization,
            @RequestParam(name = "target_id_type") String targetIdType,
            @RequestParam(name = "target_id") Long targetId);
}
