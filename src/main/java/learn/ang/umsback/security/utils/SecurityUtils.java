package learn.ang.umsback.security.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUtils {
    public static final String POLE_PREFIX = "ROLE_";

    public static SimpleGrantedAuthority convertToAuthority(String role) {
        String formattedRole = role.startsWith(POLE_PREFIX) ? role : POLE_PREFIX + role;
        return new SimpleGrantedAuthority(formattedRole);
    }
}
