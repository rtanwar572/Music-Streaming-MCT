package com.Rohit.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "auth_token")
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokId;
    private String tokValue;
    private LocalDateTime tokCreationTime;
    @Enumerated(value = EnumType.STRING)
    private Role roleType;

    public AuthToken(User user) {
        this.user = user;
        this.tokCreationTime=LocalDateTime.now();
        this.tokValue= UUID.randomUUID().toString();
        this.roleType=user.getUserRole();
    }

    @OneToOne
    @JoinColumn(name = "fk-userId")
    User user;

}
