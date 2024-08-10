package heroes.domain.auth.application;

import heroes.domain.auth.dao.RefreshTokenRepository;
import heroes.domain.auth.domain.RefreshToken;
import heroes.domain.auth.dto.AccessTokenDto;
import heroes.domain.auth.dto.RefreshTokenDto;
import heroes.domain.member.domain.Member;
import heroes.domain.member.domain.MemberRole;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public void setAuthenticationToken(Long memberId, MemberRole role) {
        UserDetails userDetails =
                User.withUsername(memberId.toString())
                        .authorities(role.toString())
                        .password("")
                        .build();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public String createAccessToken(Long memberId, MemberRole role) {
        return jwtUtil.generateAccessToken(memberId, role);
    }

    public AccessTokenDto retrieveAccessToken(String accessTokenValue) {
        try {
            return jwtUtil.parseAccessToken(accessTokenValue);
        } catch (Exception e) {
            return null;
        }
    }

    public String createRefreshToken(Long memberId) {
        String token = jwtUtil.generateRefreshToken(memberId);
        RefreshToken refreshToken = RefreshToken.builder().memberId(memberId).token(token).build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public RefreshTokenDto refreshRefreshToken(RefreshTokenDto oldRefreshTokenDto) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findById(oldRefreshTokenDto.getMemberId())
                        .orElseThrow(() -> new CustomException(ErrorCode.MISSING_JWT_TOKEN));
        RefreshTokenDto refreshTokenDto =
                jwtUtil.generateRefreshTokenDto(refreshToken.getMemberId());
        refreshToken.updateRefreshToken(refreshTokenDto.getToken());
        return refreshTokenDto;
    }

    public AccessTokenDto refreshAccessToken(Member member) {
        return jwtUtil.generateAccessTokenDto(member.getId(), member.getRole());
    }

    public RefreshTokenDto validateRefreshToken(String refreshToken) {
        return jwtUtil.parseRefreshToken(refreshToken);
    }
}
