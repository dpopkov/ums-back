package learn.ang.umsback.controller;

import learn.ang.umsback.model.User;
import learn.ang.umsback.service.AuthenticationService;
import learn.ang.umsback.service.JwtRefreshTokenService;
import learn.ang.umsback.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Endpoints:
 * <ul>
 * <li>Sign-Up => POST /api/authentication/sign-up</li>
 * <li>Sign-In => POST /api/authentication/sign-in</li>
 * <li>Refresh-Token => POST /api/authentication/refresh-token?token=</li>
 * </ul>
 */
@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtRefreshTokenService jwtRefreshTokenService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService, JwtRefreshTokenService jwtRefreshTokenService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // todo: Do NOT use entity with password hash - use dto instead
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        // todo: Do NOT use entity with password hash - use dto instead
        return new ResponseEntity<>(authenticationService.signInAndReturnJwt(user), HttpStatus.OK);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam(name = "token") String tokenId) {
        // todo: Do NOT use entity with password hash - use dto instead
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(tokenId));
    }
}
