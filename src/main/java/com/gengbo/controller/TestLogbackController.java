package com.gengbo.controller;

import com.gengbo.domain.SubResult;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created  2016/12/28-10:45
 * Author : gengbo
 */
@ToString
public class TestLogbackController {
    public static final Logger log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    public static final Logger logX = LoggerFactory.getLogger("x");
    public static final Logger logXy = LoggerFactory.getLogger("x.y");
    public static final Logger logXyz = LoggerFactory.getLogger("x.y.z");
    public static void main(String[] args) {
        log.debug("root debug");
        log.info("root info");
        log.error("root error");

        logX.debug("x debug");
        logX.info("x info");
        logX.error("x error");

        logXy.debug("xy debug");
        logXy.info("xy info");
        logXy.error("xy error");

        logXyz.debug("xyz debug");
        logXyz.info("xyz info");
        logXyz.error("xyz error");
        logXyz.error("the result is {}", new SubResult());
    }
}
