### 启动流程
#### 针对"错误: 找不到或无法加载主类"问题
> 方式一:　maven 先clean 再install 对应模块即可
> 方式二: 查看project structure, 对应报错的module, 
>　一般是对应模块引入后修改了命名导致 target 目录失效, 新建一个即可


#### 针对 cloud-eureka
> 针对单点 eureka
```text
1. 一个eureka应用启动, 优先修改hosts
修改hosts: 
127.0.0.1        euk1.com

2. 单节点应用application.properties配置:
spring.profiles.active=euk
```

> eureka 集群启动
```text
1. 三个节点高可用配置, 修改hosts(多个eureka在同一台服务器,通过ip配置无法正常启动)
127.0.0.1        euk1.com
127.0.0.1        euk2.com
127.0.0.1        euk3.com

2. 在idea启动的 Edit Configurations 中配置, Allow Parallel run
3. 逐一启动, 分别配置application.properties配置为 euk1, euk2, euk3
spring.profiles.active=euk1
spring.profiles.active=euk2
spring.profiles.active=euk3
```

#### user-api模块, 作为集中接口
> 通过maven install至本地即可
> mvn clean install -DskipTests -Dmaven.javadoc.skip=true

#### cloud-admin 可选
> 如果要启动, 开启 cloud-eureka user-consumer user-provider 配置文件中的如下配置 
> spring.boot.admin.client.url=http://localhost:8080
> pom.xml文件需要引入 spring-boot-admin-starter-client 依赖

#### cloud-config 可选
> 如果需要打开:
> 打开需要监控应用(user-consumer)pom.xml的 spring-cloud-starter-sleuth spring-cloud-starter-zipkin两个依赖
> 对应应用配置文件中的 spring.cloud.config.uri    
> spring.cloud.config.discovery.enabled    spring.cloud.config.discovery.service-id
> spring.cloud.config.profile    spring.cloud.config.label

#### 链路监控 可选
> 如果需要打开:
> 打开需要监控应用(user-consumer user-provider)pom.xml的 spring-cloud-starter-sleuth spring-cloud-starter-zipkin两个依赖
> 对应应用配置文件中的 spring.zipkin.base-url spring.sleuth.sampler.rate 注释需要打开
> 运行 https://zipkin.io/pages/quickstart.html 中下载的 zipkin-server-2.23.2-exec.jar 服务 

#### zuul 可选
> 直接启动即可, 通过应用名称(如: user-consumer user-provider)来路由访问
> 测试访问:  http://localhost/user-consumer/alive 