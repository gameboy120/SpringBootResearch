package com.gengbo.extend.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;

public class CommandObservableHelloWorld extends HystrixObservableCommand<String> {
    private final String name;
    public CommandObservableHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("CommandObservableHelloWorldGroup"));
        this.name = name;
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        System.out.println("CommandObservableHelloWorld Fallback");
        return super.resumeWithFallback();
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                System.out.println(Thread.currentThread().getName());
                try {
                    if (!observer.isUnsubscribed()) {
                        // 在真实世界，run() 方法可能会产生一些网络请求等
                        observer.onNext("Hello");
                        observer.onNext(name + "!");
                        observer.onCompleted();
//                        observer.onError(new RuntimeException("lalala"));
                    }
                } catch (Throwable e) {
                    observer.onError(e);
                }
            }
        } );

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Observable<String> world = new CommandObservableHelloWorld("World").observe();
        try {
            System.out.println(world.toBlocking().toFuture().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}