package org.example.grip_demo.congestion.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongestionDTO {
    private Integer timeZone;
    private Integer presentCount;

    public CongestionDTO(Integer timeZone, Integer presentCount) {
        this.timeZone = timeZone;
        this.presentCount = presentCount;
    }
}
