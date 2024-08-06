package org.example.grip_demo.climbinggym.interfaces;

import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.condemo.CongestionDemoDto;

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
    private List<CongestionDemoDto> congestions;

    public ClimbingGymDto(Long id, String name, String address, Float mapX, Float mapY, int acceptableCount, List<CongestionDemoDto> congestions) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.acceptableCount = acceptableCount;
        this.congestions = congestions;
    }
}
