package org.example.grip_demo.congestion.domain;

import jakarta.persistence.*;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;

import java.time.LocalDateTime;

@Entity
public class Congestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TIME_ZONE_RATIO", nullable = false)
    private double timeZoneRatio;

    @Column(name ="TIME_ZONE", nullable = false)
    private int timeZone;

    @Column(name="PRESENT_COUNT", nullable = false)
    private int presentCount;

    @Column(name="ACCEPTABLE_COUNT", nullable = false)
    private int acceptableCount;

    @ManyToOne
    @JoinColumn(name="CLIMBING_GYM_ID", nullable = false)
    private ClimbingGym climbingGym;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalDateTime endTime;


    public Congestion() {
        this.createdAt = LocalDateTime.now();
    }

    public Congestion(double timeZoneRatio, int timeZone, int presentCount, int acceptableCount, LocalDateTime startTime, LocalDateTime endTime){
        this.timeZoneRatio = timeZoneRatio;
        this.timeZone = timeZone;
        this.presentCount = presentCount;
        this.acceptableCount = acceptableCount;
        this.createdAt = LocalDateTime.now();
        this.climbingGym = climbingGym;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public double getTimeZoneRatio() {
        return timeZoneRatio;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public int getAcceptableCount() {
        return acceptableCount;
    }

    public ClimbingGym getClimbingGym() {
        return climbingGym;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}