package com.gengbo.controller;

import com.gengbo.annotation.Matrix;
import com.gengbo.extend.CatHttpClientUtils;
import com.gengbo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created  2016/12/16-17:50
 * Author : gengbo
 */
@RestController
//@EnableAutoConfiguration
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello World~";
    }

    @Matrix(name = "g", type = "b")
    @RequestMapping(value = "/createOrder/{id}", method = RequestMethod.GET)
    public List<Article> createOrder(@PathVariable("id") String id) throws IOException {
        System.out.println(request);
        try {
            CatHttpClientUtils.get(new URI("http://localhost:8080/redirect/a" + id + "?date=11-12-31"), new HashMap<String, String>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
