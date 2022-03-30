package learn.ang.umsback.service;

import learn.ang.umsback.model.Role;
import learn.ang.umsback.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeUserRole(Role role, String username);

    List<User> finaAllUsers();
}
