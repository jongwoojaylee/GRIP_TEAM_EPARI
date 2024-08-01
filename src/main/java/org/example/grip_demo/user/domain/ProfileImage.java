package org.example.grip_demo.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "PROFILEIMAGES")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String path;

    @OneToMany(mappedBy = "profileImage")
    public List<User> users = new ArrayList<>();
}
