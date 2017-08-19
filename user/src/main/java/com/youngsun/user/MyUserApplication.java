package com.youngsun.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.youngsun.user.mapper")
public class MyUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyUserApplication.class, args);
	}
}
