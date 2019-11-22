package net.medrag.config;

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
        entityManagerFactoryRef = "oldDSEmFactory",
        transactionManagerRef = "oldDSTransactionManager",
        basePackages = {"net.medrag.old_database.repository"})
public class OldDatabaseConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("spring.old.datasource")
    public DataSourceProperties oldDSProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource oldDS(
            @Qualifier("oldDSProperties") DataSourceProperties oldDSProperties) {
        return oldDSProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean oldDSEmFactory(
            @Qualifier("oldDS") DataSource oldDS, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(oldDS)
                .packages("net.medrag.old_database.model")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager oldDSTransactionManager(EntityManagerFactory oldDSEmFactory) {
        return new JpaTransactionManager(oldDSEmFactory);
    }
}
