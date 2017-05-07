package com.gengbo.controller;

import com.gengbo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  2016/12/17-23:16
 * Author : gengbo
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/ttl")
    public long ttl(String key) {
        return redisService.ttl(key);
    }

    @RequestMapping(value = "setnx")
    public boolean setnx(String key, String value) {
        return redisService.setnx(key, value);
    }

    @RequestMapping(value = "set")
    public void set(String key, String value, long time) {
        redisService.set(key, value, time);
    }

    @RequestMapping(value = "delete")
    public void delete(String key) {
        redisService.delete(key);
    }

    @RequestMapping(value = "exist")
    public boolean exist(String key) {
        return redisService.exist(key);
    }


}
