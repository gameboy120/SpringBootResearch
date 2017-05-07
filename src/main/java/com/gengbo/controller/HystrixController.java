package com.gengbo.controller;

import com.gengbo.extend.hystrix.HystrixUtil;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Value("${service.hotel.url}")
    private String HOTEL_URL;

    @Value("${service.hotel.name}")
    private String hotelServiceName;
    
    @Value("${service.hotel.method.getHotelInfo}")
    private String hotelServiceMethodGetHotelInfo;
    
    @Autowired
    private HystrixUtil hystrixUtil;

    @RequestMapping(value = "/firstHystrix", method = RequestMethod.GET)
    public String getHotelInfo(@RequestParam("id") int id, @RequestParam("name") String name) {
        String url = String.format(HOTEL_URL, id, name);
        Response response = null;
        try {
            response = hystrixUtil.execute(hotelServiceName, hotelServiceMethodGetHotelInfo, url);
            if (response != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (response != null && response.body() != null) {
                try {
                    response.body().close();// 资源关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "获取酒店信息失败";
    }

}