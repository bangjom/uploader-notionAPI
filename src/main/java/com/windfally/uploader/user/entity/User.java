package com.windfally.uploader.user.entity;

import com.windfally.uploader.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column
    private String joinBy;

    @ManyToMany
    @JoinTable(
            name = "users_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
