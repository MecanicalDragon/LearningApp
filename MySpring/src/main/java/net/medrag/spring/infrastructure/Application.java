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
        Configuration configuration = new PropertiesFileConfiguration(reader);
        BeanInitializer initializer = new BeanInitializer();
        ApplicationContext context = new ApplicationContext(configuration, initializer);
        context.startContext();
        return context;
    }
}
