package com.gengbo.extend;

import com.gengbo.annotation.Matrix;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created  2016/12/21-17:25
 * Author : gengbo
 */
@Aspect
@Component
public class MatrixInterceptor {

    @Around("within(com.gengbo..*) && @annotation(matrix)")
    public void handleMatrix(ProceedingJoinPoint point, Matrix matrix) throws Throwable {
        System.out.println("Matrix init...");
        System.out.println(matrix.name());
        System.out.println(matrix.type());
        point.proceed();
        System.out.println("Matrix end...");
    }
}
