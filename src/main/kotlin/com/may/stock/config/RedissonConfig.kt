package com.may.stock.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig {
    @Value("\${spring.redis.host}")
    private val redisHost: String? = null

    @Value("\${spring.redis.port}")
    private val redisPort = 0

    @Bean
    fun redissonClient(): RedissonClient? {
        val config = Config()
        config.useSingleServer().address = "$REDISSON_HOST_PREFIX$redisHost:$redisPort"
        return Redisson.create(config)
    }

    companion object {
        private const val REDISSON_HOST_PREFIX = "redis://"
    }
}