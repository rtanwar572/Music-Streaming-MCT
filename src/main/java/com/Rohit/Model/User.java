package com.Rohit.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer userId;
    @NotBlank
    private String userName;
    //    private String userContact;
    @Email
    private String userEmail;
    @Enumerated(value = EnumType.STRING)
    private Role userRole;
    private String userPass;

}
