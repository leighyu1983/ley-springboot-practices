package com.ley;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Leigh Yu
 * @date 2020/8/24 21:56
 */
@Configuration
public class HiveConfig {
    @Value("${hive.url}")
    private String url;

    @Value("${hive.driver-class-name}")
    private String driver;

    @Value("${hive.user}")
    private String user;

    @Value("${hive.password}")
    private String password;

    @Bean
    public DataSource dataSource(){
        DataSource dataSource = new DataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
