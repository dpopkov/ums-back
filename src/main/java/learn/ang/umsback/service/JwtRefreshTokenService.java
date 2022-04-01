package learn.ang.umsback.service;

import learn.ang.umsback.model.JwtRefreshToken;
import learn.ang.umsback.model.User;

public interface JwtRefreshTokenService {

    JwtRefreshToken createRefreshToken(Long userId);

    User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
