package com.gengbo.extend;

import com.dianping.cat.message.Transaction;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  2016/12/19-16:48
 * Author : gengbo
 */
public class CatContext {
//    public static final String ROOT = "cat.root.msg.id";
//    public static final String PARENT = "cat.parent.msg.id";
//    public static final String CHILD = "cat.child.msg.id";
    private static final ThreadLocal<Map<String, String>> property = new ThreadLocal<>();
    private static ThreadLocal<Transaction> serviceLocal = new ThreadLocal<Transaction>();
    private static ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();
    private static ThreadLocal<Transaction> pageLocal = new ThreadLocal<Transaction>();

    public static void setServiceTransaction(Transaction serviceTransaction) {
        serviceLocal.set(serviceTransaction);
    }

    public static void setCatTransaction(Transaction catTransaction) {
        tranLocal.set(catTransaction);
    }

    public static void setPageTransaction(Transaction pageTransaction) {
        pageLocal.set(pageTransaction);
    }

    public static void completeServiceTransaction(Object obj) {
        Transaction transaction = serviceLocal.get();
        if (null != transaction) {
            transaction.setStatus(obj.toString());
            transaction.complete();
        }
    }

    public static void completeCatTransaction(Object obj) {
        Transaction transaction = tranLocal.get();
        if (null != transaction) {
            transaction.setStatus(obj.toString());
            transaction.complete();
        }
    }

    public static void completePageTransaction(Object obj) {
        Transaction transaction = pageLocal.get();
        if (null != transaction) {
            transaction.setStatus(obj.toString());
            transaction.complete();
        }
    }

    public static void addProperty(String name, String value) {
        Map<String, String> map = property.get();
        if (CollectionUtils.isEmpty(map)) {
            property.set(new HashMap<String, String>());
        }
        property.get().put(name, value);
    }

    public static String getProperty(String name) {
        return property.get().get(name);
    }

    public static void clear() {
        property.remove();
        serviceLocal.remove();
        tranLocal.remove();
        pageLocal.remove();
    }
}
