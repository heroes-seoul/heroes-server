package heroes.global.security;

import static heroes.global.common.constants.SecurityConstants.TOKEN_PREFIX;

import heroes.domain.auth.application.JwtTokenService;
import heroes.domain.auth.dto.AccessTokenDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessTokenHeaderValue = extractAccessTokenFromHeader(request);

        if (accessTokenHeaderValue != null) {
            AccessTokenDto accessTokenDto =
                    jwtTokenService.retrieveAccessToken(accessTokenHeaderValue);
            if (accessTokenDto != null) {
                jwtTokenService.setAuthenticationToken(
                        accessTokenDto.getMemberId(), accessTokenDto.getMemberRole());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
