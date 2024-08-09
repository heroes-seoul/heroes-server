package heroes.domain.auth.application;

import static heroes.domain.member.domain.MemberRole.COMPANY;
import static heroes.domain.member.domain.MemberRole.USER;

import heroes.domain.auth.dto.request.AuthCodeLoginRequest;
import heroes.domain.auth.dto.response.KakaoTokenLoginResponse;
import heroes.domain.auth.dto.response.TokenPairResponse;
import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.member.dao.MemberRepository;
import heroes.domain.member.domain.Member;
import heroes.domain.member.domain.OauthInfo;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final KakaoService kakaoService;
    private final IdTokenVerifier idTokenVerifier;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenService jwtTokenService;

    public TokenPairResponse socialLogin(AuthCodeLoginRequest request) {
        KakaoTokenLoginResponse response = kakaoService.getIdToken(request.getCode());
        OidcUser oidcUser = idTokenVerifier.getOidcUser(response.getId_token());
        OauthInfo oauthInfo = OauthInfo.from(oidcUser);

        Member member = getMemberByOidcInfo(oidcUser, oauthInfo, request.getRole());
        jwtTokenService.setAuthenticationToken(member.getId(), member.getRole());
        return getLoginResponse(member);
    }

    private TokenPairResponse getLoginResponse(Member member) {
        String accessToken = jwtTokenService.createAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtTokenService.createRefreshToken(member.getId());
        return new TokenPairResponse(accessToken, refreshToken);
    }

    private Member getMemberByOidcInfo(OidcUser oidcUser, OauthInfo oauthInfo, String role) {
        return memberRepository
                .findByOauthInfo(oauthInfo)
                .orElseGet(() -> saveMember(oauthInfo, getUserSocialName(oidcUser), role));
    }

    private String getUserSocialName(OidcUser oidcUser) {
        return oidcUser.getClaim("nickname");
    }

    private Member saveMember(OauthInfo oauthInfo, String username, String role) {
        if (role != null && role.equals(COMPANY.toString())) {
            Company company = companyRepository.save(Company.createEmptyCompany());
            return memberRepository.save(Member.createCompanyMember(oauthInfo, username, company));
        } else if (role != null && role.equals(USER.toString())) {
            return memberRepository.save(Member.createNormalMember(oauthInfo, username));
        }
        throw new CustomException(ErrorCode.NOT_EXISTED_ROLE);
    }
}
