package com.gengbo;

import rx.Observable;
import rx.Subscriber;

/**
 * Created  2017/1/4-18:22
 * Author : gengbo
 */
public class RxJavaTest {

    public static void main(String[] args) {
        Observable.OnSubscribe<String> onSubscriber1 = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onCompleted();
            }
        };
        Subscriber<String> subscriber1 = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("complete");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("erroe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("next:" + s);
            }
        };

        Observable.create(onSubscriber1)
                .subscribe(subscriber1);
    }
}
