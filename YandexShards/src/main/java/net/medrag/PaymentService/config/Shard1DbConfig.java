package net.medrag.PaymentService.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "shard1EmFactory",
        transactionManagerRef = "shard1DSTransactionManager",
        basePackages = {"net.medrag.PaymentService.repository.shard1"})
public class Shard1DbConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.shard1.datasource")
    public DataSourceProperties shard1DSProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource shard1DataSource(
            @Qualifier("shard1DSProperties") DataSourceProperties shard1DataSource) {
        return shard1DataSource.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean shard1EmFactory(
            @Qualifier("shard1DataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(datasource)
                .packages("net.medrag.PaymentService.model.entity")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager shard1DSTransactionManager(EntityManagerFactory shard1EmFactory) {
        return new JpaTransactionManager(shard1EmFactory);
    }
}
