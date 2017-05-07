package com.gengbo.extend.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MyHystrixCommand extends HystrixCommand<Response> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyHystrixCommand.class);
    private String url;

    public MyHystrixCommand(Setter setter, String url) {
        super(setter);
        this.url = url;
    }

    @Override
    protected Response run() throws Exception {
        LOGGER.info("服务正被调用，当前线程：'{}'", Thread.currentThread().getName());
        Request request = new Request.Builder().url(url).build();
        return new OkHttpClient().newCall(request).execute();
    }

    @Override
    public Response getFallback() {
        LOGGER.error("服务调用失败,service:'{}'");
        return null;
    }
}