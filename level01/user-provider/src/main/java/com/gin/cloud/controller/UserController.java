package com.gin.cloud.controller;

import com.gin.cloud.api.UserApi;
import com.gin.cloud.vo.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class UserController implements UserApi {

    private AtomicInteger count = new AtomicInteger();

    @Value("${server.port}")
    private String port;

    /*
    //未使用 implements UserApi 之前调用url,地址由本地Controller
    //http://localhost:84/register
    @GetMapping("alive")
    @Override
    public String alive(){
        return "alive ok";
    }

    //http://localhost:84/register
    @GetMapping("register")
    @Override
    public String register() {
        return "register ok";
    }*/

    // 继承了接口,则Getmapping这些注解从接口获取即可
    // 实际请求地址以接口类和方法上注解拼接为准
    //http://localhost:84/user/register
    @Override
    public String alive(){
        try {
            //测试降级
            //int gin = 1/0;
            //Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("error:" + e);
        }

        int idx = count.getAndIncrement();
        System.out.println("provider alive port=" + port + " count=" + idx);
        return "provider alive port=" + port + " count=" + idx ;
    }

    //http://localhost:83/user/register
    @Override
    public String register(Integer id) {
        int idx = count.getAndIncrement();
        System.out.println("provider register port=" + port + " id=" + id);
        return "provider register port=" + port + " id=" + id;
    }

    @Override
    // @RequestParam()中的value表示接口参数名, 接口定义的为方法成员变量名
    // 两者名称一致可以只写@RequestParam
    public Map<Integer, String> getMap(@RequestParam("id") Integer id) {
        System.out.println(id);
        return Collections.singletonMap(id, "memeda");
    }
    @Override
    public Map<Integer, String> getMap2(Integer id,String name) {
        System.out.println(id);
        return Collections.singletonMap(id, name);
    }

    @Override
    public Map<Integer, String> getMap3(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        return Collections.singletonMap(Integer.parseInt(map.get("id").toString()), map.get("name").toString());
    }

    @Override
    public Map<Integer, String> postMap(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        return Collections.singletonMap(Integer.parseInt(map.get("id").toString()), map.get("name").toString());
    }

    @Override
    public Map<Integer, String> postPerson(Person person) {
        System.out.println(person);
        return Collections.singletonMap(person.getId(), person.getName());
    }

}
