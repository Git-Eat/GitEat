package com.giteat.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // 환경변수에서 데이터베이스 설정 가져오기
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        String dbUsername = System.getenv("SPRING_DATASOURCE_USERNAME");
        String dbPassword = System.getenv("SPRING_DATASOURCE_PASSWORD");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }
}
