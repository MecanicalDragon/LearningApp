package net.medrag.spring.infrastructure;

import net.medrag.spring.infrastructure.api.Configuration;
import net.medrag.spring.infrastructure.api.ConfigurationReader;
import net.medrag.spring.infrastructure.config.*;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class Application {

    public static ApplicationContext run() {

        PackageReader packageReader = new PackageReader("net.medrag.spring");
        Configuration configuration = new ReflectionsConfiguration(packageReader);
        BeanInitializer initializer = new BeanInitializer(packageReader);

//        Configuration configuration = new PropertiesFileConfiguration(new PropertiesConfigurationReader());
//        BeanInitializer initializer = new BeanInitializer(new PropertiesInfrastructureReader());

        ApplicationContext context = new ApplicationContext(configuration, initializer);
        context.startContext();
        return context;
    }
}
