package com.gin.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author gin
 * Hystrix 包装 restTemplate 方式
 */
@Service
public class RestService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(defaultFallback = "aliveBack")
    public String alive() {
        String url = "http://localhost:84/alive";
        return restTemplate.getForObject(url, String.class);
    }

    public String aliveBack() {
        return "restTemplate Hystrix 降级了";
    }

}
