package net.medrag.config;

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
        entityManagerFactoryRef = "newDSEmFactory",
        transactionManagerRef = "newDSTransactionManager",
        basePackages = {"net.medrag.new_database.repository"})
public class NewDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties newDSProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource newDS(
            @Qualifier("newDSProperties") DataSourceProperties newDSProperties) {
        return newDSProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean newDSEmFactory(
            @Qualifier("newDS") DataSource newDS, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(newDS)
                .packages("net.medrag.new_database.model")
                .build();
    }

    @Bean
    public PlatformTransactionManager newDSTransactionManager(EntityManagerFactory newDSEmFactory) {
        return new JpaTransactionManager(newDSEmFactory);
    }
}
