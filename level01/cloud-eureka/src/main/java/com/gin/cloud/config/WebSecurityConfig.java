package com.gin.cloud.config;

/*import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;*/

/**
 * 配置了 spring-cloud-starter-security 依赖情况下使用
 * 需要允许跨域访问
 * 否则其他服务无法注册
 */
/*
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		super.configure(http);
	}

}*/
public class WebSecurityConfig {

}