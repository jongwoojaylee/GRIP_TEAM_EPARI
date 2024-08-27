package org.example.grip_demo.congestion.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongestionDto {
    private Integer timeZone;
    private Integer presentCount;

    public CongestionDto(int timeZone, int presentCount) {
        this.timeZone = timeZone;
        this.presentCount = presentCount;
    }
}
