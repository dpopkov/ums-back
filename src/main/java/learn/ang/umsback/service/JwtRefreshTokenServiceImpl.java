package learn.ang.umsback.service;

import learn.ang.umsback.model.JwtRefreshToken;
import learn.ang.umsback.model.User;
import learn.ang.umsback.repository.JwtRefreshTokenRepository;
import learn.ang.umsback.repository.UserRepository;
import learn.ang.umsback.security.UserPrincipal;
import learn.ang.umsback.security.jwt.JwtProvider;
import learn.ang.umsback.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService {

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_EXPIRATION_IN_MS;

    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public JwtRefreshTokenServiceImpl(JwtRefreshTokenRepository jwtRefreshTokenRepository, UserRepository userRepository, JwtProvider jwtProvider) {
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtRefreshToken createRefreshToken(Long userId) {
        JwtRefreshToken token = new JwtRefreshToken();
        token.setTokenId(UUID.randomUUID().toString());
        token.setUserId(userId);
        token.setCreateDate(LocalDateTime.now());
        token.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));
        return jwtRefreshTokenRepository.save(token);
    }

    @Override
    public User generateAccessTokenFromRefreshToken(String refreshTokenId) {
        JwtRefreshToken refreshToken = jwtRefreshTokenRepository.findById(refreshTokenId).orElseThrow();
        if (refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("JWT refresh token is not valid");
        }
        User user = userRepository.findById(refreshToken.getUserId()).orElseThrow();
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(user.getRole().name())))
                .build();
        String accessToken = jwtProvider.generateToken(userPrincipal);
        user.setAccessToken(accessToken);
        user.setRefreshTokenId(refreshTokenId);
        return user;
    }
}
