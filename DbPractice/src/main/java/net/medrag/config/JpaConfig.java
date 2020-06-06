package net.medrag.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * {@author} Stanislav Tretyakov
 * 14.02.2020
 */
@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Value("${dbapp.datasource.driverClassName}")
    String driverClassName;
    @Value("${dbapp.datasource.url}")
    String datasourceUrl;
    @Value("${dbapp.datasource.username}")
    String dbUsername;
    @Value("${dbapp.datasource.password}")
    String dbPassword;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("net.medrag.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public ClassicConfiguration flywayConfig() {
        ClassicConfiguration c = new ClassicConfiguration();
        c.setDataSource(dataSource());
        c.setBaselineOnMigrate(true);
        return c;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(flywayConfig());
    }

    @Bean
    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "none");
                setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                setProperty("hibernate.globally_quoted_identifiers", "true");
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.format_sql", "false");
                setProperty("hibernate.use_sql_comments", "true");
            }
        };
    }
}
