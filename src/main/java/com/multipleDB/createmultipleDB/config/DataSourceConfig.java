package com.multipleDB.createmultipleDB.config;

import com.multipleDB.createmultipleDB.DataSource.MultiTenantDataSourceRouter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String defaultUrl;

    @Value("${spring.datasource.username}")
    private String defaultUsername;

    @Value("${spring.datasource.password}")
    private String defaultPassword;

    @Bean
    public DataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        // Configure default data source
        DataSource defaultDataSource = DataSourceBuilder.create()
                .url(defaultUrl)
                .username(defaultUsername)
                .password(defaultPassword)
                .build();

        targetDataSources.put("default", defaultDataSource);

        // Add additional company-specific data sources here
        DataSource company1DataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/company1_db")
                .username(defaultUsername)
                .password(defaultPassword)
                .build();

        targetDataSources.put("company1", company1DataSource);

        MultiTenantDataSourceRouter routingDataSource = new MultiTenantDataSourceRouter();
        routingDataSource.setDefaultTargetDataSource(defaultDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }
}
