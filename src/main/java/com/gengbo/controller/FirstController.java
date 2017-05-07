package com.gengbo.controller;

import com.gengbo.model.Article;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  2016/12/16-17:50
 * Author : gengbo
 */
@RestController
@EnableAutoConfiguration
public class FirstController {

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public List<Article> getArticle() throws IOException {
        List<Article> result = new ArrayList<>(10);
        Article article = Article.builder().id("1").title("title").build();
        result.add(article);

        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:8080/redirect/a124?date=11-12-31");

            HttpResponse execute = client.execute(get);
            System.out.println(execute.getStatusLine().getStatusCode());

        return result;
    }

}
