package com.gengbo.annotation;

import java.lang.annotation.*;

/**
 * Created  2016/12/21-16:49
 * Author : gengbo
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Matrix {
    String type();
    String name();
    String status() default "0";
    String data() default "";
}
