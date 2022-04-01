package learn.ang.umsback.controller;

import learn.ang.umsback.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.finaAllUsers());
    }
}
