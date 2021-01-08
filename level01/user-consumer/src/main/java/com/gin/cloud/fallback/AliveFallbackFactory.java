package com.gin.cloud.fallback;

import com.gin.cloud.api.ConsumerUserApi;
import com.gin.cloud.vo.Person;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gin
 * FallbackFactory 比 AliveFallback(后面有保留)优势在于可以根据自定义异常返回特定信息
 * AliveFallback需要在ConsumerUserApi上添加 @FeignClient(name = "user-provider", fallback = AliveFallback.class)
 */
@Component
public class AliveFallbackFactory implements FallbackFactory<ConsumerUserApi> {
    @Override
    public ConsumerUserApi create(Throwable throwable) {
        return new ConsumerUserApi() {
            @Override
            public String alive() {
                //可以根据 throwable 进行判断是否为业务逻辑上的bug
                // 比如 InternalServerException
                throwable.printStackTrace();
                if (throwable instanceof FeignException.InternalServerError){
                    return "谁写bug了?500";
                }
                System.out.println("throwable=" + ToStringBuilder.reflectionToString(throwable));
                return "工厂降级了";
            }

            @Override
            public String register(Integer id) {
                return null;
            }

            @Override
            public Map<Integer, String> getMap(Integer id) {
                return null;
            }

            @Override
            public Map<Integer, String> getMap2(Integer id, String name) {
                return null;
            }

            @Override
            public Map<Integer, String> getMap3(Map<String, Object> map) {
                return null;
            }

            @Override
            public Map<Integer, String> postMap(Map<String, Object> map) {
                return null;
            }

            @Override
            public Map<Integer, String> postPerson(Person person) {
                return null;
            }
        };
    }
}

/*
package com.gin.cloud.fallback;

import com.gin.cloud.api.ConsumerUserApi;
import com.gin.cloud.vo.Person;
import org.springframework.stereotype.Component;

import java.util.Map;

// 需要将 AliveFallback 交给Spring托管(单例,不需要多个)
@Component("aliveFallback")
public class AliveFallback implements ConsumerUserApi {
    @Override
    public String alive() {
        System.out.println("AliveFallback alive");
        return "降级了";
    }

    @Override
    public String register(Integer id) {
        System.out.println("AliveFallback register");
        return "降级了";
    }

    @Override
    public Map<Integer, String> getMap(Integer id) {
        return null;
    }

    @Override
    public Map<Integer, String> getMap2(Integer id, String name) {
        return null;
    }

    @Override
    public Map<Integer, String> getMap3(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<Integer, String> postMap(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<Integer, String> postPerson(Person person) {
        return null;
    }
}

 */