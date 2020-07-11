package net.medrag.spring;

import net.medrag.spring.application.service.api.SpaceProject;
import net.medrag.spring.infrastructure.Application;
import net.medrag.spring.infrastructure.ApplicationContext;

/**
 * @author Stanislav Tretyakov
 * 10.07.2020
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run();
        SpaceProject mars = (SpaceProject) context.getBean("mars");
        mars.launchRocketToSpace();
    }
}
