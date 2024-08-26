package org.example.grip_demo.user.domain;

import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 7*24*60*60)
public class RedisRefreshToken {
    @Id
    private Long userId;
    private String refreshToken;

    public RedisRefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
