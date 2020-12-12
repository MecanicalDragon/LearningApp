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
        entityManagerFactoryRef = "shard3EmFactory",
        transactionManagerRef = "shard3DSTransactionManager",
        basePackages = {"net.medrag.PaymentService.repository.shard3"})
public class Shard3DbConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties shard3DSProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource shard3DataSource(
            @Qualifier("shard3DSProperties") DataSourceProperties shard3DataSource) {
        return shard3DataSource.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean shard3EmFactory(
            @Qualifier("shard3DataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(datasource)
                .packages("net.medrag.PaymentService.model.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager shard3DSTransactionManager(@Qualifier("shard3EmFactory") EntityManagerFactory shard3EmFactory) {
        return new JpaTransactionManager(shard3EmFactory);
    }
}

