package org.example.grip_demo.climbinggym.interfaces;

import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.congestion.interfaces.CongestionDto;

import java.util.List;

@Getter
@Setter
public class ClimbingGymDto {
    private Long id;
    private String name;
    private String address;
    private String roadAddress;
    private Float mapX;
    private Float mapY;
    private String telephone;
    private int acceptableCount;
    private List<CongestionDto> congestions;

    public ClimbingGymDto(Long id, String name, String address, Float mapX, Float mapY, int acceptableCount, List<CongestionDto> congestions) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.acceptableCount = acceptableCount;
        this.congestions = congestions;
    }

    public ClimbingGymDto(Long id, String name, String address, Float mapX, Float mapY) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public ClimbingGymDto(Long id, String name, String address, Float mapX, Float mapY, int acceptableCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.acceptableCount = acceptableCount;
    }
}
