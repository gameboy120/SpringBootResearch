package com.gengbo.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 测试跟disconf整合
 * Created  2016/12/29-16:27
 * Author : gengbo
 */
@Getter
@Setter
public class TestConfig {
    private String key;
    private int timeoutInMillions;

}
