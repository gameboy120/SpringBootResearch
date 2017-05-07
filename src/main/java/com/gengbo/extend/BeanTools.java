package com.gengbo.extend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanTools implements ApplicationContextAware {
    private static ApplicationContext applicationContext;


    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> classname) {
        T _restTemplate = applicationContext.getBean(classname);
        if (null == _restTemplate) {
            ApplicationContext parent = applicationContext.getParent();
            if (null != parent) {
                _restTemplate = parent.getBean(classname);
            }
        }
        return _restTemplate;
    }


    public static void setApplicationContext1(ApplicationContext context) {
        applicationContext = context;
    }
}