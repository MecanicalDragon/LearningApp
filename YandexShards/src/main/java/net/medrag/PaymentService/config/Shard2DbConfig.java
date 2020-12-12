package net.medrag.PaymentService.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "shard2EmFactory",
        transactionManagerRef = "shard2DSTransactionManager",
        basePackages = {"net.medrag.PaymentService.repository.shard2"})
public class Shard2DbConfig {

    @Bean
    @ConfigurationProperties("spring.shard2.datasource")
    public DataSourceProperties shard2DSProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource shard2DataSource(
            @Qualifier("shard2DSProperties") DataSourceProperties shard2DataSource) {
        return shard2DataSource.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean shard2EmFactory(
            @Qualifier("shard2DataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(datasource)
                .packages("net.medrag.PaymentService.model.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager shard2DSTransactionManager(@Qualifier("shard2EmFactory") EntityManagerFactory shard2EmFactory) {
        return new JpaTransactionManager(shard2EmFactory);
    }
}
