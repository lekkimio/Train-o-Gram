package com.example.trainogram.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    private String avatar;

//    @Column(name = "role", columnDefinition = "VARCHAR(20)")
//    @Enumerated(value = EnumType.STRING)
//    private Role role;

    @Column(nullable = false)
//    @Convert(converter = RoleConverter.FieldConverter.class)
    @Enumerated(EnumType.STRING)
    private Role role;

//    private boolean deleted;

}
