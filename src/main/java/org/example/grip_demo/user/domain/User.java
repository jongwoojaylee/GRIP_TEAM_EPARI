package org.example.grip_demo.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String address;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    private Boolean gender = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "PROFILE_IMAGES_ID")
    private ProfileImage profileImage;


    @OneToMany(mappedBy = "user")
    private Set<RefreshToken> refreshTokens;


}
