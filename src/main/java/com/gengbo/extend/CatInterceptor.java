package com.gengbo.extend;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CatInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Transaction t = Cat.newTransaction("URL", request.getRequestURL().toString());
        CatContext.setCatTransaction(t);
        System.out.println("CatInterceptor preHandle...");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        String viewName = modelAndView != null?modelAndView.getViewName():"无";
        Transaction t = Cat.newTransaction("View", viewName);
        CatContext.setPageTransaction(t);
        System.out.println("CatInterceptor postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //请求-页面渲染前
        CatContext.completePageTransaction(Transaction.SUCCESS);

        //总计
        CatContext.completeCatTransaction(Transaction.SUCCESS);
        System.out.println("CatInterceptor afterCompletion...");
    }

}
