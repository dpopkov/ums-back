package learn.ang.umsback.controller;

import learn.ang.umsback.model.Role;
import learn.ang.umsback.security.UserPrincipal;
import learn.ang.umsback.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("change/{role}")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @PathVariable Role role) {
        userService.changeUserRole(role, userPrincipal.getUsername());
        return ResponseEntity.ok(true);
    }
}
