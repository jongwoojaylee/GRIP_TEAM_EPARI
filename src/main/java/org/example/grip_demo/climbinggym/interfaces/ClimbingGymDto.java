package org.example.grip_demo.climbinggym.interfaces;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    public ClimbingGymDto(Long id, String name, String address, Float mapX, Float mapY) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
    }
}
