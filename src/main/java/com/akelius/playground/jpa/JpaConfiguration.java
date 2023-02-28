package com.akelius.playground.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "jpaEntityManager",
        basePackages = "com.akelius.playground.jpa", transactionManagerRef = "jpaTransactionManager")
@Slf4j
public class JpaConfiguration {

    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean jpaEntityManager(
            final EntityManagerFactoryBuilder builder) {
        final LocalContainerEntityManagerFactoryBean entityManager = builder
                .dataSource(userDataSource())
                .packages("com.akelius.playground.jpa")
                .build();

        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.cache.use_second_level_cache",
                env.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
        properties.put("hibernate.cache.region.factory_class",
                env.getProperty("spring.jpa.properties.hibernate.cache.region.factory_class"));
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Primary
    @Bean
    public DataSource userDataSource() {
        final String jdbcUrl = env.getProperty("jdbc.url");
        log.info("Creating changelog datasource for database url {}", jdbcUrl);
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        final String driverClassName = env.getProperty("jdbc.driverClassName");
        if (driverClassName != null) {
            dataSource.setDriverClassName(driverClassName);
        }
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

    @Autowired
    public void setEnv(final Environment env) {
        this.env = env;
    }
}
