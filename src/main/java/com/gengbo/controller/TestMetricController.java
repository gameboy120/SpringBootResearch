package com.gengbo.controller;

import com.dianping.cat.Cat;
import com.gengbo.model.Article;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created  2016/12/16-17:50
 * Author : gengbo
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/test/metric")
public class TestMetricController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello World~";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public List<Article> testCount() throws IOException {

        try {
            Cat.logMetricForCount("test-MetricCount");
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }



    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    public List<Article> testSum() throws IOException {

        try {
            Cat.logMetricForDuration("test-MetricTime", (int)(Math.random()*100));
            Cat.logMetricForSum("test-MetricSum", 1);
            TimeUnit.MILLISECONDS.sleep((int)(Math.random()*100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


}
