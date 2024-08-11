package heroes.domain.auth.application;

import static heroes.domain.member.domain.MemberRole.COMPANY;
import static heroes.domain.member.domain.MemberRole.USER;

import heroes.domain.auth.dao.RefreshTokenRepository;
import heroes.domain.auth.dto.AccessTokenDto;
import heroes.domain.auth.dto.RefreshTokenDto;
import heroes.domain.auth.dto.request.AuthCodeLoginRequest;
import heroes.domain.auth.dto.request.TokenRefreshRequest;
import heroes.domain.auth.dto.response.KakaoTokenLoginResponse;
import heroes.domain.auth.dto.response.TokenPairResponse;
import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.member.dao.MemberRepository;
import heroes.domain.member.domain.Member;
import heroes.domain.member.domain.OauthInfo;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.MemberUtil;
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
    private final MemberUtil memberUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenPairResponse socialLogin(AuthCodeLoginRequest request) {
        KakaoTokenLoginResponse response = kakaoService.getIdToken(request.getCode());
        OidcUser oidcUser = idTokenVerifier.getOidcUser(response.getId_token());
        OauthInfo oauthInfo = OauthInfo.from(oidcUser);

        Member member = getMemberByOidcInfo(oidcUser, oauthInfo, request.getRole());
        jwtTokenService.setAuthenticationToken(member.getId(), member.getRole());
        return getLoginResponse(member);
    }

    public TokenPairResponse tokenRefresh(TokenRefreshRequest request) {
        RefreshTokenDto oldRefreshTokenDto =
                jwtTokenService.validateRefreshToken(request.getRefreshToken());

        if (oldRefreshTokenDto == null) {
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
        RefreshTokenDto newRefreshTokenDto =
                jwtTokenService.refreshRefreshToken(oldRefreshTokenDto);
        AccessTokenDto accessTokenDto =
                jwtTokenService.refreshAccessToken(getMember(newRefreshTokenDto));
        return new TokenPairResponse(accessTokenDto.getToken(), newRefreshTokenDto.getToken());
    }

    public void memberLogout() {
        final Member currentMember = memberUtil.getCurrentMember();
        deleteRefreshToken(currentMember);
    }

    public void memberWithdrawal() {
        final Member currentMember = memberUtil.getCurrentMember();
        kakaoService.withdrawal(Long.parseLong(currentMember.getOauthInfo().getOauthId()));
        deleteRefreshToken(currentMember);
        memberRepository.delete(currentMember);
    }

    private void deleteRefreshToken(Member currentMember) {
        refreshTokenRepository
                .findById(currentMember.getId())
                .ifPresent(refreshTokenRepository::delete);
    }

    private Member getMember(RefreshTokenDto refreshTokenDto) {
        return memberRepository
                .findById(refreshTokenDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
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
