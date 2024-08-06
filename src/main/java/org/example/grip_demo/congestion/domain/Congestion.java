package org.example.grip_demo.congestion.domain;

import jakarta.persistence.*;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;

@Entity
public class Congestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="TIME_ZONE", nullable = false)
    private int timeZone;

    @Column(name="PRESENT_COUNT", nullable = false)
    private int presentCount;

    @ManyToOne
    @JoinColumn(name="CLIMBING_GYM_ID", nullable = false)
    private ClimbingGym climbingGym;


    public Congestion() {
    }

    public Congestion(int timeZone, int presentCount, ClimbingGym climbingGym){
        this.timeZone = timeZone;
        this.presentCount = presentCount;
        this.climbingGym = climbingGym;
    }

    public Long getId() {
        return id;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public ClimbingGym getClimbingGym() {
        return climbingGym;
    }
}