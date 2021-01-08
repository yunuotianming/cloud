package com.gin.cloud.api;

import com.gin.cloud.fallback.AliveFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 不结合eureka，就是自定义一个client名字。就用url属性指定 服务器列表。url=“http://ip:port/”
 * 使用 cloud, 则使用虚拟主机名(vip), cloud-provider
 */
//@FeignClient(name = "udfName_ooxx", url = "localhost:83")

// 方式一: 注意 Hystrix 和 feign 整合,需要将 AliveFallback 交给Spring托管(单例,不需要多个)
// 另: AliveFallback 只能实现 ConsumerUserApi(本项目api),
// 如果实现 UserApi (公用api)会报 fallback is not assignable to interface 公共api接口(回滚类不能分配给公共api接口)
//@FeignClient(name = "user-provider", fallback = AliveFallback.class)

// 方式二: 用于处理可能存在500的保存,需要通知开发人员,不能简单返回降级数据(如简单空数据 或者 "刷新一下")
@FeignClient(name = "user-provider", fallbackFactory = AliveFallbackFactory.class)
public interface ConsumerUserApi extends UserApi {


    //1.自定义接口,需要提供文档
/*    //虽然不需要自己通过 + 进行字符串拼接url,但这里的数据来源还是服务提供方提供的文档
    //这种适合跨平台(含其他语言), java开发更适合(api接口)
    @GetMapping("/alive")
    //open feign可以识别spring mvc注解,帮我们把地址拼接成: http://user-provider/alive 进行调用
    String alive();

    @GetMapping("/register")
    String register();*/

    //2.使用统一接口,那么直接继承公共api的interface即可

}