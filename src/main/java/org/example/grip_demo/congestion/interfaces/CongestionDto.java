package org.example.grip_demo.congestion.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongestionDto {
    private int timeZone;
    private int presentCount;

    public CongestionDto(int timeZone, int presentCount) {
        this.timeZone = timeZone;
        this.presentCount = presentCount;
    }
}
