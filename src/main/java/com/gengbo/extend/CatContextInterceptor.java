package com.gengbo.extend;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.google.common.base.Strings;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class CatContextInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("CatContextInterceptor preHandle...");
        String rootMsgId = request.getHeader(Cat.Context.ROOT);
        String parentMsgId = request.getHeader(Cat.Context.PARENT);
        String childMsgId = request.getHeader(Cat.Context.CHILD);

        if (!Strings.isNullOrEmpty(rootMsgId) && !Strings.isNullOrEmpty(parentMsgId) &&
                !Strings.isNullOrEmpty(childMsgId)) {
            Transaction serviceTransaction = Cat.newTransaction("service", request.getRequestURL().toString());
            final HashMap<String, String> map = new HashMap<>();
            Cat.Context context = new Cat.Context() {
                @Override
                public void addProperty(String key, String value) {
                    map.put(key, value);
                }

                @Override
                public String getProperty(String key) {
                    return map.get(key);
                }
            };
            context.addProperty(Cat.Context.ROOT, rootMsgId);
            context.addProperty(Cat.Context.PARENT, parentMsgId);
            context.addProperty(Cat.Context.CHILD, childMsgId);
            Cat.logRemoteCallServer(context);
            CatContext.setServiceTransaction(serviceTransaction);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("CatContextInterceptor postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        CatContext.completeServiceTransaction(Transaction.SUCCESS);
        CatContext.clear();
        System.out.println("CatContextInterceptor afterCompletion...");
    }

}
