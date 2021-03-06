package com.gengbo.extend.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

public class CommandUsingRequestCacheInvalidation {
    /* 表示一个远程数据源 */
    private static volatile String prefixStoredOnRemoteDataStore = "ValueBeforeSet_";
    public static class GetterCommand extends HystrixCommand<String> {
        private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("GetterCommand");
        private final int id;
        public GetterCommand(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet"))
                    .andCommandKey(GETTER_KEY));
            this.id = id;
        }
        @Override
        protected String run() {
            return prefixStoredOnRemoteDataStore + id;
        }
        @Override
        protected String getCacheKey() {
            return String.valueOf(id);
        }
        /**
         * 用于刷新缓存
         * 
         * @param id 命令构造器上的入参
         */
        public static void flushCache(int id) {
            HystrixRequestCache.getInstance(GETTER_KEY,
                    HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
        }
    }
    public static class SetterCommand extends HystrixCommand<Void> {
        private final int id;
        private final String prefix;
        public SetterCommand(int id, String prefix) {
            super(HystrixCommandGroupKey.Factory.asKey("GetSetGet"));
            this.id = id;
            this.prefix = prefix;
        }
        @Override
        protected Void run() {
            // 持久化数据
            prefixStoredOnRemoteDataStore = prefix;
            // 刷新缓存
            GetterCommand.flushCache(id);
            // 无返回值
            return null;
        }
    }
}