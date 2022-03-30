package learn.ang.umsback.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents user that is used for sign-in and sing-up operations.
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 80)
    private String username;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
