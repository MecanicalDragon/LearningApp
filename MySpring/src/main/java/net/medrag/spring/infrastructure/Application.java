package net.medrag.spring.infrastructure;

import net.medrag.spring.infrastructure.api.Configuration;
import net.medrag.spring.infrastructure.api.ConfigurationReader;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class Application {

    public static ApplicationContext run() {

        ConfigurationReader reader = new PropertiesConfigurationReader();
        ConfigurationReader infra = new PropertiesInfrastructureReader();

        Configuration configuration = new PropertiesFileConfiguration(reader);
        BeanInitializer initializer = new BeanInitializer(infra);

        ApplicationContext context = new ApplicationContext(configuration, initializer);
        context.startContext();
        return context;
    }
}
