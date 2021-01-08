package com.gin.cloud.controller;

import com.gin.cloud.service.RestService;
import com.gin.cloud.api.ConsumerUserApi;
import com.gin.cloud.vo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
// spring config 中配置文件变更了,通过 RefreshScope 来保持热部署(热部署时服务不可用,类似重启)
// 通过使用(POST请求) http://localhost:94/actuator/refresh 来刷新对应的服务配置(微服务多,需要通过bus 企业消息总线实现)
@RefreshScope
public class HystrixController {

    @Autowired
    private ConsumerUserApi api;

    @Autowired
    private RestService restService;

    @Value("${config.info}")
    private String info;

    @Value("${server.port}")
    private String port;

    @GetMapping("config")
    public String getConfig(){
        System.out.println("info=" + info);
        return info;
    }

    @GetMapping("alive")
    public String alive(){
        String result = "consumer alive port=" + port + " " + api.alive();
        System.out.println(result);
        return result;
    }

    @GetMapping("alive2")
    public String alive2(){
        String result = "consumer alive port=" + port + " " + restService.alive();
        System.out.println(result);
        return result;
    }

    @GetMapping("register")
    public String register(@RequestParam Integer id){
        String result = "consumer register port=" + port + " " + api.register(id);
        System.out.println(result);
        return result;
    }

    @GetMapping("/getMap")
    public Map<Integer, String> map(@RequestParam Integer id) {
        System.out.println(id);
        Map<Integer, String> map = api.getMap(id);
        map.put(null, port);
        return map;
    }

    @GetMapping("/getMap2")
    public Map<Integer, String> map2(@RequestParam Integer id, @RequestParam String name) {
        System.out.println(id);
        return api.getMap2(id,name);
    }

    @GetMapping("/getMap3")
    public Map<Integer, String> map3(@RequestParam Map<String, Object> map) {
        System.out.println("map=" + map);
        System.out.println(map);
        return api.getMap3(map);
    }

    @GetMapping("/postMap")
    //这里是为了测试消费端调用方便,没使用post
    //@PostMapping("/postMap")
    public Map<Integer, String> postMap(@RequestParam Map<String, Object> map) {
		System.out.println("map=" + map);
        System.out.println(map);
        return api.postMap(map);
    }

    @GetMapping("/postPerson")
    //这里是为了测试消费端调用方便,没使用post
    //@PostMapping("/postPerson")
    public Map<Integer, String> postPerson(@RequestParam Integer id, @RequestParam String name) {
        return api.postPerson(new Person(id, name));
    }

}
