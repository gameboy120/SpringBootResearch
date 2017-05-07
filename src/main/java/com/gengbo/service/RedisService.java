package com.gengbo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    public StringRedisTemplate template;
    @Autowired
    public RedisTemplate redisTemplate;

    public long ttl(String key) {
        return this.template.getExpire(key);
    }

    public boolean setnx(String key, String value) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.setIfAbsent(key, value);
    }

    public void set(String key, String value, long time) {
        BoundValueOperations<String, String> ops = this.template.boundValueOps(key);
        ops.set(value, time, TimeUnit.SECONDS);
    }

    public void delete(String key) {
        this.template.delete(key);
    }

    public boolean exist(String key) {
        return this.template.hasKey(key);
    }

    public void leftPush(String key, String value) {
        this.template.boundListOps(key).leftPush(value);
    }

    public String rightPop(String key) {
        return this.template.boundListOps(key).rightPop();
    }
}