package com.gengbo;

import java.util.concurrent.TimeUnit;

/**
 * Created  2017/1/5-10:28
 * Author : gengbo
 */
public class DeadLockTest {
    public static final Object lockA = new Object();
    public static final Object lockB = new Object();

    public static void main(String[] args) {
        new Thread("t1"){
            public void run() {
                synchronized (lockA) {
                    System.out.println("lock a");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lockB) {
                        System.out.println("lock b");
                    }
                }
            }
        }.start();
        new Thread("t2"){
            public void run() {
                synchronized (lockB) {
                    System.out.println("lock b");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lockA) {
                        System.out.println("lock a");
                    }
                }
            }
        }.start();
        new Thread("t3"){
            public void run() {
                while (true) {
                }
            }
        }.start();

        new Thread("t4"){
            public void run(){
                while (true) {
                    try {
                        System.out.println("sleep 5 s");
                        TimeUnit.SECONDS.sleep(5);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
