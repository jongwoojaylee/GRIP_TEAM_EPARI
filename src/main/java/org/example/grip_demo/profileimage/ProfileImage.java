package org.example.grip_demo.profileimage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.user.domain.User;


@Entity
@Getter @Setter
@Table(name = "PROFILE_IMAGES")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "PATH", length = 512, nullable = false)
    private String path;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
