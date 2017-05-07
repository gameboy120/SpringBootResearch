package com.gengbo;

import com.gengbo.extend.hystrix.*;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created  2017/1/3-14:46
 * Author : gengbo
 */
public class HystrixTest {

    @Test
    public void testObservable() throws Exception {
        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();
        // 阻塞模式
        assertEquals("Hello World!", fWorld.toBlocking().single());
        assertEquals("Hello Bob!", fBob.toBlocking().single());
        // 非阻塞模式
        // - 匿名内部类形式，本测试不做任何断言
        fWorld.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                // nothing needed here
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }
        });
        // 非阻塞模式
        // - 同样是匿名内部类形式，忽略“异常”和“完成”回调
        fBob.subscribe(new Action1<String>() {
            @Override
            public void call(String v) {
                System.out.println("onNext: " + v);
            }
        });
        fWorld.subscribe(s -> {
            System.out.println(s);
        },Throwable::printStackTrace,() -> {
            System.out.println("sdfasdf");
        });
    }

    @Test
    public void testSynchronous() {
        assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
        assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
    }

    @Test
    public void testWithoutCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            assertTrue(new CommandUsingRequestCache(2).execute());
            assertFalse(new CommandUsingRequestCache(1).execute());
            assertTrue(new CommandUsingRequestCache(0).execute());
            assertTrue(new CommandUsingRequestCache(58672).execute());
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testWithCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            CommandUsingRequestCache command2a = new CommandUsingRequestCache(2);
            CommandUsingRequestCache command2b = new CommandUsingRequestCache(2);
            assertTrue(command2a.execute());
            // 这是第一次执行命令，结果未命中缓存
            assertFalse(command2a.isResponseFromCache());
            assertTrue(command2b.execute());
            // 这是第二次执行命令，结果“2”命中缓存
            assertTrue(command2b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
        // 创建一个新的上下文（缓存为空）
        context = HystrixRequestContext.initializeContext();
        try {
            CommandUsingRequestCache command3b = new CommandUsingRequestCache(2);
            assertTrue(command3b.execute());
            // 此时缓存为空，结果未命中缓存
            assertFalse(command3b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testCollapser() throws Exception {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
            Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
            Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
            Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();
            Future<String> f5 = new CommandCollapserGetValueForKey(5).queue();
            assertEquals("ValueForKey: 1", f1.get());
            assertEquals("ValueForKey: 2", f2.get());
            assertEquals("ValueForKey: 3", f3.get());
            assertEquals("ValueForKey: 4", f4.get());
            assertEquals("ValueForKey: 5", f5.get());
            // 断言合并请求 'GetValueForKey' 只执行了一次
            assertEquals(1, HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
            HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
            // 断言发出的请求是我们期望的请求
            assertEquals("GetValueForKey", command.getCommandKey().name());
            // 断言请求是否通过“合并”的方式发出
            assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
            // 并且被成功执行
            assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testSuccess() {
        assertEquals("success", new CommandThatFailsFast(false).execute());
    }
    @Test
    public void testFailure() {
        try {
            new CommandThatFailsFast(true).execute();
            fail("we should have thrown an exception");
        } catch (HystrixRuntimeException e) {
            assertEquals("failure from CommandThatFailsFast", e.getCause().getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testFailure2() {
        try {
            assertEquals(null, new CommandThatFailsSilently(true).execute());
        } catch (HystrixRuntimeException e) {
            fail("we should not get an exception as we fail silently with a fallback");
        }
    }

    private class MyTest{
        public static final String name = "";

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        HystrixTest test = new HystrixTest();
        MyTest t = test.new MyTest();
    }
}
