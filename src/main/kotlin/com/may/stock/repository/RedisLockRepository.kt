package com.may.stock.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun lock(key: String): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent(key, "lock", Duration.ofMillis(3_000))!! // setnx
    }

    fun unlock(key: String): Boolean {
        return redisTemplate.delete(key)
    }
}
