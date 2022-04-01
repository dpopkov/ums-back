package learn.ang.umsback.service;

import learn.ang.umsback.model.JwtRefreshToken;
import learn.ang.umsback.model.User;
import learn.ang.umsback.security.UserPrincipal;
import learn.ang.umsback.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final JwtRefreshTokenService jwtRefreshTokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider, JwtRefreshTokenService jwtRefreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @Override
    public User signInAndReturnJwt(User signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);
        User signInUser = userPrincipal.getUser();
        signInUser.setAccessToken(jwt);
        JwtRefreshToken refreshToken = jwtRefreshTokenService.createRefreshToken(signInUser.getId());
        signInUser.setRefreshTokenId(refreshToken.getTokenId());
        return signInUser;
    }
}
