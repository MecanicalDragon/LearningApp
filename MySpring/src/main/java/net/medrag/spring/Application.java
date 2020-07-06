package net.medrag.spring;

import net.medrag.spring.application.service.api.SpaceProject;
import net.medrag.spring.infrastructure.ApplicationContext;
import net.medrag.spring.infrastructure.BeanInitializer;
import net.medrag.spring.infrastructure.DefaultConfiguration;
import net.medrag.spring.infrastructure.PropertiesConfigurationReader;
import net.medrag.spring.infrastructure.api.Configuration;
import net.medrag.spring.infrastructure.api.ConfigurationReader;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class Application {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ConfigurationReader reader = new PropertiesConfigurationReader();
        Configuration configuration = new DefaultConfiguration(reader.readConfiguration());
        BeanInitializer initializer = new BeanInitializer();
        ApplicationContext context = new ApplicationContext(configuration, initializer);
        context.startContext();
        SpaceProject mars = (SpaceProject) context.getBean("mars");
        mars.launchRocketToSpace();
    }
}
