package com.takeoff.magic_cafe_rider.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class CommonDBConfig {
    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        return props;
    }

    protected DataSourceProperties getDataSourceProperties() {
        return new DataSourceProperties();
    }

    protected DataSource getDataSource() {
        return dataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    protected EntityManagerFactoryBuilder.Builder getEntityManagerFactoryBuilder(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .properties(jpaProperties());
    }

    protected PlatformTransactionManager getTransactionManager(
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(
                Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }

    public abstract DataSourceProperties dataSourceProperties();
    public abstract DataSource dataSource();
}
