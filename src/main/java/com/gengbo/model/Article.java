package com.gengbo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * Created  2016/12/17-14:16
 * Author : gengbo
 */
@Builder
@Setter
@Getter
//@Entity
//@Table(name = "article")
public class Article {

//    @Id
//    @Column(
//            name = "id", nullable = false
//    )
    private String id;

//    @Column(
//            name = "title", nullable = false, unique = true
//    )
    private String title;
}
