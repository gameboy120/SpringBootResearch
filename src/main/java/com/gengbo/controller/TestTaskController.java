package com.gengbo.controller;

import com.gengbo.task.ASyncCallbackTask;
import com.gengbo.task.ASyncTask;
import com.gengbo.task.SyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created  2016/12/16-17:50
 * Author : gengbo
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/tast")
public class TestTaskController {
    @Autowired
    private SyncTask syncTask;
    @Autowired
    private ASyncTask aSyncTask;
    @Autowired
    private ASyncCallbackTask aSyncCallbackTask;

    @RequestMapping(value = "async/req")
    public Callable<String> callable() {
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return () -> {
            Thread.sleep(1000L);
            return "小单 - " + System.currentTimeMillis();
        };
    }

    @RequestMapping(value = "sync/req")
    public String syncReq() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小单 - " + System.currentTimeMillis();
    }

    @RequestMapping(value = "sync", method = RequestMethod.GET)
    public void syncTask() {
        try {
            syncTask.doTaskOne();
            syncTask.doTaskTwo();
            syncTask.doTaskThree();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "async", method = RequestMethod.GET)
    public void asyncTask() {
        try {
            aSyncTask.doTaskOne();
            aSyncTask.doTaskTwo();
            aSyncTask.doTaskThree();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "asynccallback", method = RequestMethod.GET)
    public void asyncCallbackTask() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = aSyncCallbackTask.doTaskOne();
        Future<String> task2 = aSyncCallbackTask.doTaskTwo();
        Future<String> task3 = aSyncCallbackTask.doTaskThree();
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                break;
            }
            System.out.println("sleep 1 sec");
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}
