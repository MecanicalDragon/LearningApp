package net.medrag.microservices.openapiexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Stanislav Tretyakov
 * 04.03.2021
 */
@Configuration
public class HsqldbConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setSeparator(";;")
                .addScript("db/V1__init_db.sql")
                .addScript("db/V2__fill_db.sql")
                .build();
    }
}
