package org.example.grip_demo.climbinggym.domain;

import jakarta.persistence.*;

@Entity
@Table(name="Climbing_gyms")
public class ClimbingGym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private Long mapX;

    @Column(nullable = false)
    private Long mapY;

    @Column(nullable = true)
    private String telephone;

}
