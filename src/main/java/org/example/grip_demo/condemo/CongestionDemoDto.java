package org.example.grip_demo.condemo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongestionDemoDto {
    private Integer timeZone;
    private Integer presentCount;

    public CongestionDemoDto(Integer timeZone, Integer presentCount) {
        this.timeZone = timeZone;
        this.presentCount = presentCount;
    }
}
