package org.example.grip_demo.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "REFRESH_TOKENS")
@Getter @Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String value;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

}
