package com.takeoff.magic_cafe_rider.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "DBEntityManagerFactory",
        transactionManagerRef = "DBTransactionManager",
        basePackages = {"com.takeoff.magic_cafe_rider.repository",
                "com.takeoff.magic_cafe_rider.model"})
public class DBConfig extends CommonDBConfig {

    @Bean(name = "DBDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return getDataSourceProperties();
    }

    @Bean(name = "DBDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        return getDataSource();
    }

    @Bean(name = "DBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return getEntityManagerFactoryBuilder(builder)
                .packages("com.takeoff.magic_cafe_rider.repository",
                        "com.takeoff.magic_cafe_rider.model")
                .build();
    }

    @Bean(name = "DBTransactionManager")
    public PlatformTransactionManager transactionManager(
            final @Qualifier("DBEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                    localContainerEntityManagerFactoryBean) {
        return getTransactionManager(localContainerEntityManagerFactoryBean);
    }
}
