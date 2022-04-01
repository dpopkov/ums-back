package learn.ang.umsback.service;

import learn.ang.umsback.model.User;

public interface AuthenticationService {
    User signInAndReturnJwt(User signInRequest);
}
