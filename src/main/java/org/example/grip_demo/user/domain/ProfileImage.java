package org.example.grip_demo.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "PROFILE_IMAGES")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "PATH", length = 512, nullable = false)
    private String path;

    @OneToMany(mappedBy = "profileImage")
    private Set<User> users;
}
