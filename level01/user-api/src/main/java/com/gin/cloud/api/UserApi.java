package com.gin.cloud.api;

import com.gin.cloud.vo.Person;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户操作相关接口
 * 通过 maven install 至本地,以便各个不同模块可以通过统一接口进行开发
 *
 * 注意: 接口定义时,注解尽量别少,feign会读取spring mvc的注解来配置请求路径,封装参数
 *
 * @author gin
 *
 */
//注意: 单独使用feign可以在类上添加 @RequestMapping
// 如果使用 feign + hystrix 在类上添加会出现重复注册 url 的问题
// SpringMVC 会 查看对应注解, feign会看一次, hystrix也会查看一次(hystrix存在重复注册)
//@RequestMapping("/cloud")
public interface UserApi {

	@GetMapping("/alive")
	String alive();

	@GetMapping("/register")
	String register(@RequestParam Integer id);

	@GetMapping("/getMap")
	Map<Integer, String> getMap(@RequestParam("id") Integer id);

	@GetMapping("/getMap2")
	Map<Integer, String> getMap2(@RequestParam("id") Integer id,
								 @RequestParam("name") String name);

	@GetMapping("/getMap3")
	Map<Integer, String> getMap3(@RequestParam Map<String, Object> map);

	//注意接口定义时,注解尽量别少,feign会读取spring mvc的注解来配置请求路径,封装参数
	@PostMapping("/postMap")
	Map<Integer, String> postMap(@RequestBody Map<String, Object> map);

	@PostMapping("/postPerson")
	Map<Integer, String> postPerson(@RequestBody Person person);


}