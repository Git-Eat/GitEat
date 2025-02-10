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
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://i12b108.p.ssafy.io:3307/giteat?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("gozldgkwlakfk108");
        return dataSource;
    }
}
