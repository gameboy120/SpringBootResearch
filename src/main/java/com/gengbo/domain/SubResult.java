package com.gengbo.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SubResult extends Result {
    private String code = "1";
}