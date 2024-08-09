package heroes.domain.auth.application;

import heroes.domain.auth.dao.RefreshTokenRepository;
import heroes.domain.auth.domain.RefreshToken;
import heroes.domain.member.domain.MemberRole;
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

    public String createRefreshToken(Long memberId) {
        String token = jwtUtil.generateRefreshToken(memberId);
        RefreshToken refreshToken = RefreshToken.builder().memberId(memberId).token(token).build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }
}
