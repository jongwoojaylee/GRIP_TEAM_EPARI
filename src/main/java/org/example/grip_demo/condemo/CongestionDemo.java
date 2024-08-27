package org.example.grip_demo.condemo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;

@Entity
@Table(name = "Congestions")
@Setter
@Getter
public class CongestionDemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "time_zone")
    private Integer timeZone;
    @Column(name = "PRESENT_COUNT")
    private Integer presentCount;
    @ManyToOne
    @JoinColumn(name="CLIMBING_GYM_ID", nullable = false)
    private ClimbingGym climbingGym;
}
