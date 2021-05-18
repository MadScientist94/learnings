package com.example.withOutOrm;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class OracleConfig {
@Bean("orcl")
DataSource dataSource() throws SQLException {
    OracleDataSource dataSource = new OracleDataSource();
    dataSource.setUser("scott");
    dataSource.setPassword("tiger");
    dataSource.setURL("jdbc:oracle:thin:@Lenovo-PC:1522:orcl");
    dataSource.setImplicitCachingEnabled(true);
    dataSource.setDataSourceName("oracle.jdbc.driver.OracleDriver");
  
    return dataSource;
}
}
