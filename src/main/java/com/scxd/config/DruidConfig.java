package com.scxd.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Druid数据库连接池配置
 */
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        //初始连接数
        dataSource.setInitialSize(1);
        //最小连接数
        dataSource.setMinIdle(3);
        //最大连接数
        dataSource.setMaxActive(20);
        //配置获取连接等待超时的时间
        dataSource.setMaxWait(60000);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(30000);
        //打开PSCache
        dataSource.setPoolPreparedStatements(true);
        //指定每个连接上PSCache的大小
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return dataSource;
    }
    
}
