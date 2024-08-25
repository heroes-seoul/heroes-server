package heroes.domain.auth.api;

import static heroes.global.common.constants.SecurityConstants.REDIRECT_LOGIN_CODE;

import heroes.domain.auth.application.AuthService;
import heroes.domain.auth.dto.request.TokenRefreshRequest;
import heroes.domain.auth.dto.response.TokenPairResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "인증 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "일반 유저 회원가입 및 로그인", description = "일반 유저의 회원가입 및 로그인을 진행합니다.")
    @GetMapping("/login/normal")
    public TokenPairResponse normalMemberOauthLogin(
            @RequestParam(REDIRECT_LOGIN_CODE) String code) {
        return authService.socialNormalLogin(code);
    }

    @Operation(summary = "기업 유저 회원가입 및 로그인", description = "기업 유저의 회원가입 및 로그인을 진행합니다.")
    @GetMapping("/login/company")
    public TokenPairResponse companyMemberOauthLogin(
            @RequestParam(REDIRECT_LOGIN_CODE) String code) {
        return authService.socialCompanyLogin(code);
    }

    @Operation(summary = "토큰 재발급", description = "엑세스 토큰 및 리프테시 토큰을 모두 재발급합니다.")
    @PostMapping("/refresh")
    public TokenPairResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        return authService.tokenRefresh(request);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> memberLogout() {
        authService.memberLogout();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 진행합니다.")
    @DeleteMapping("/withdrawal")
    public ResponseEntity<Void> memberWithdrawal() {
        authService.memberWithdrawal();
        return ResponseEntity.ok().build();
    }
}
