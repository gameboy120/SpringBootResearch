package com.gengbo;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import com.gengbo.extend.CatContextInterceptor;
import com.gengbo.extend.CatInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@ServletComponentScan
@EnableAutoConfiguration
@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.gengbo"})
@ImportResource({"classpath:disconf.xml"})
public class Application extends WebMvcConfigurerAdapter {

    public HandlerInterceptor getMyInterceptor() {
        return new CatInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CatContextInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        return new ServletRegistrationBean(new MyServlet(), "/g/*");
//    }

    @Bean
    public ServletRegistrationBean logbackServletRegist() {
        return new ServletRegistrationBean(new ViewStatusMessagesServlet(), "/lb");
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        return new FilterRegistrationBean(new MyFilter(), servletRegistrationBean());
//    }

//    @Bean
//    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
//        return new ServletListenerRegistrationBean(new MyListener());
//    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(30*1000L); //tomcat默认10秒
        configurer.setTaskExecutor(mvcTaskExecutor());//所借助的TaskExecutor
    }
    @Bean
    public ThreadPoolTaskExecutor mvcTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("springboot-mvc-");
        executor.setCorePoolSize(2);
        executor.setQueueCapacity(1);
        executor.setMaxPoolSize(5);
        return executor;

    }


}
