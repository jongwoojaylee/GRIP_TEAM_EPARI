package org.example.grip_demo.climbinggym.interfaces;

import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.condemo.CongestionDemo;
import org.example.grip_demo.condemo.CongestionDemoDto;

import java.util.List;
import java.util.stream.Collectors;

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

    public ClimbingGymDto(Long id, String name, String address, Float mapX, Float mapY) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
    }

//    public ClimbingGymDto(ClimbingGym gym, List<CongestionDemo> gymCongestions) {
//        this.id = gym.getId();
//        this.name = gym.getName();
//        this.address = gym.getAddress();
//        this.roadAddress = gym.getRoadAddress();
//        this.mapX = gym.getMapX();
//        this.mapY = gym.getMapY();
//        this.telephone = gym.getTelephone();
//        this.acceptableCount = gym.getAcceptableCount();
//
//        // CongestionDemo 리스트를 CongestionDemoDto로 변환
//        this.congestions = gymCongestions.stream()
//                .map(CongestionDemoDto::new)
//                .collect(Collectors.toList());
//    }
}
