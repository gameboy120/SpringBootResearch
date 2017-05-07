package com.gengbo;

import org.springframework.core.env.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  2017/1/23-13:48
 * Author : gengbo
 */
public class Test {
    public static void main(String[] args) {
        Environment environment = new StandardEnvironment();
        System.out.println(environment.getProperty("file.encoding"));
    }

    @org.junit.Test
    public void testResolver() {
        MutablePropertySources propertySources = new MutablePropertySources();
        Map<String, Object> map = new HashMap<>();
        map.put("encoding", "gbk");
        PropertySource propertySource1 = new MapPropertySource("map", map);
        propertySources.addFirst(propertySource1);
        PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);

        System.out.println(propertyResolver.resolveRequiredPlaceholders("wo le ge qu de ${encoding1}"));
    }
}
