package com.gengbo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created  2017/1/5-14:01
 * Author : gengbo
 */
public class LockConditionTest {
    public static final Lock lock = new ReentrantLock();
    public static final Condition full = lock.newCondition();
    public static final Condition empty = lock.newCondition();
    public List<String> data;
    public int size;
    public LockConditionTest() {
        this(10);
    }
    public LockConditionTest(int size) {
        this.data = new ArrayList<>(size);
        this.size = size;
        for (int i = 0; i < size; i++) {
            data.add("" + i);
        }
    }

    public String get() throws InterruptedException {

        try {
            lock.lock();
            while (data.size() == 0) {
                System.out.println("empty await");
                empty.await();
            }
            String item = data.remove(data.size() - 1);
            full.signalAll();
            return  item;
        } finally {
            lock.unlock();
        }
    }

    public void put(String item) throws InterruptedException {
        try {
            lock.lock();
            while (data.size() == size) {
                System.out.println("full await");
                full.await();
            }
            data.add(item);
            empty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockConditionTest test = new LockConditionTest();
//        new Thread(() -> {
//            while (true) {
//
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                    System.out.println(test.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    test.put("" + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (true) {

        }
    }
}
