package net.medrag.PaymentService.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ConditionalOnProperty(
        name = "spring.flyway.enabled",
        havingValue = "true")
@Configuration
public class FlywayConfig {

    @Autowired
    private ApplicationContext context;

    @Value("${payment.service.database.schema}")
    private String schema;

    @Bean(initMethod = "migrate")
    public Flyway shard1Flyway() {
        ClassicConfiguration c = new ClassicConfiguration();
        c.setDataSource((DataSource) context.getBean("shard1DataSource"));
        c.setLocations(new Location("db/shard1"));
        c.setBaselineOnMigrate(true);
        c.setSchemas(schema);
        return new Flyway(c);
    }

    @Bean(initMethod = "migrate")
    public Flyway shard2Flyway() {
        ClassicConfiguration c = new ClassicConfiguration();
        c.setDataSource((DataSource) context.getBean("shard2DataSource"));
        c.setLocations(new Location("db/shard2"));
        c.setBaselineOnMigrate(true);
        c.setSchemas(schema);
        return new Flyway(c);
    }

    @Bean(initMethod = "migrate")
    public Flyway shard3Flyway() {
        ClassicConfiguration c = new ClassicConfiguration();
        c.setDataSource((DataSource) context.getBean("shard3DataSource"));
        c.setLocations(new Location("db/shard3"));
        c.setBaselineOnMigrate(true);
        c.setSchemas(schema);
        return new Flyway(c);
    }
}
