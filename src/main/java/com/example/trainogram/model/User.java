package com.example.trainogram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String avatar;

//    @Column(name = "role", columnDefinition = "VARCHAR(20)")
//    @Enumerated(value = EnumType.STRING)
//    private Role role;

    @Column(nullable = false)
//    @Convert(converter = RoleConverter.FieldConverter.class)
    @Enumerated(EnumType.STRING)
    private Role role;

}
