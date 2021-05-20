package com.oracle.jpa;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
@Autowired
@Qualifier("orcl")
DataSource ds;
@Bean("jt")
JdbcTemplate get1() {
	JdbcTemplate jt= new JdbcTemplate(ds);
	return jt;
}
}
