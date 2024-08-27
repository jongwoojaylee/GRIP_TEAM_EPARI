package org.example.grip_demo.condemo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongestionDemoDto {
    private Long id;
    private Integer timeZone;
    private Integer presentCount;

    public CongestionDemoDto(Integer timeZone, Integer presentCount) {
        this.timeZone = timeZone;
        this.presentCount = presentCount;
    }
//    public CongestionDemoDto(CongestionDemo congestion) {
//        this.id = congestion.getId();
//        this.presentCount = congestion.getPresentCount();
//        this.timeZone = congestion.getTimeZone();
//    }
}
