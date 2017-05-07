package com.gengbo.extend.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class CommandHelloWorld extends HystrixCommand<String> {
    private final String name;
    private static final Setter cacheSetter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandHelloWorldGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool"));
    public CommandHelloWorld(String name) {
        super(cacheSetter);
        this.name = name;
    }
    @Override
    protected String run() {
        // 在真实世界，run() 方法可能会产生一些网络请求等
        System.out.println(Thread.currentThread().getName());
        return "Hello " + name + "!";
    }


}