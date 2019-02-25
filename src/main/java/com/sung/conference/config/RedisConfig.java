package com.sung.conference.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PreDestroy;
import java.util.Optional;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.port")
    private int port;

    private Optional<RedisServer> redis;

    public RedisConfig(){
        redis = Optional.of(new RedisServer(port));
        redis.get().start();
    }

    @PreDestroy
    public void stopRedis(){
        if(redis.isPresent()){
           redis.get().stop();
        }
    }

}
