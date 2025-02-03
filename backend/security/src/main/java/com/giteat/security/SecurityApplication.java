package com.giteat.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
}



//TODO : 프론트에서는 요청을 로그인만 요청하게 코드가 작성이되어있다.
//TODO : 이전에 사용했떤 refresh , 틀린 refresh를 던진다.
//썌얘