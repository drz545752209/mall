package com.hlju.mall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private String timeouts;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private String maxWaitMilliss;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "jedisPool")
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        long maxWaitMillis = Long.parseLong(maxWaitMilliss.replace("ms", ""));
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        int timeout = Integer.parseInt(timeouts.replace("ms", ""));
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }

}
