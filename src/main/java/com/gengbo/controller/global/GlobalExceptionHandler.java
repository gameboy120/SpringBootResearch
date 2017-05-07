package com.gengbo.controller.global;

import com.gengbo.extend.CatContext;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        try {
            //请求-页面渲染前
            CatContext.completePageTransaction(e);

            //总计
            CatContext.completeCatTransaction(e);
            //服务交叉调用
            CatContext.completeServiceTransaction(e);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            CatContext.clear();
        }

        return mav;
    }

}
