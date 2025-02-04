package com.giteat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.giteat.user.model.dao")
public class GiteatApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiteatApplication.class, args);
	}

}
