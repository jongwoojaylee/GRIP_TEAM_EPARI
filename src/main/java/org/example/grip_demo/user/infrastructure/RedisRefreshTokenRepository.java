package org.example.grip_demo.user.infrastructure;

import org.example.grip_demo.user.domain.RedisRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, Long> {
}
