package com.gengbo.controller;

import com.gengbo.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  2016/12/16-17:50
 * Author : gengbo
 */
@RestController
@EnableAutoConfiguration
public class DisconfController {
    @Autowired
    private TestConfig testConfig;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {

        return "Hello World~" + "DisconfConfig:" + testConfig.getKey();
    }



}
