package com.multipleDB.createmultipleDB.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {

    public EntityManager createEntityManager(String dbName) {
        DataSource dataSource = createDataSource(dbName);
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory(dataSource);
        return entityManagerFactory.createEntityManager();
    }

    private DataSource createDataSource(String dbName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName + "?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    private EntityManagerFactory createEntityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(dataSource);
        emFactory.setPackagesToScan("com.multipleDB.createmultipleDB.entity");
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Hibernate properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        emFactory.setJpaPropertyMap(properties);

        emFactory.afterPropertiesSet();

        return emFactory.getObject();
    }

    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

