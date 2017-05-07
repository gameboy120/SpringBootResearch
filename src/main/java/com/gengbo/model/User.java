package com.gengbo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created  2016/12/19-18:29
 * Author : gengbo
 */
@Getter
@Setter
@Component
public class User implements Serializable {

    private static final long serialVersionUID = 359729887302709787L;
    private Long id;
    private String username;
    private String password;

}
