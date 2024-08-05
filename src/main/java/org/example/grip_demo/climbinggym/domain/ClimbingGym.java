package org.example.grip_demo.climbinggym.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="Climbing_gyms")
public class ClimbingGym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String roadAddress;

    @Column(nullable = false)
    private Float mapX;

    @Column(nullable = false)
    private Float mapY;

    @Column(nullable = true)
    private String telephone;

    @Column(name="ACCEPTABLE_COUNT", nullable = false)
    private int acceptableCount;
}

