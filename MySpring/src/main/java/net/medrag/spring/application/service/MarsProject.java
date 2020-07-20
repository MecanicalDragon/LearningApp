package net.medrag.spring.application.service;

import net.medrag.spring.application.domain.Plot;
import net.medrag.spring.application.domain.Rocket;
import net.medrag.spring.application.service.api.*;
import net.medrag.spring.infrastructure.annotations.AutoInject;
import net.medrag.spring.infrastructure.annotations.Bean;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
@Bean(id = "mars")
public class MarsProject implements SpaceProject {

    @AutoInject
    private ConstructionTeam constructionTeam;
    @AutoInject
    private DevelopmentTeam developmentTeam;
    @AutoInject
    private LaunchingTeam launchingTeam;
    @AutoInject(beanId = "baikonur")
    private PreparingTeam preparingTeam;

    @Override
    public void launchRocketToSpace() {
        Plot plot = developmentTeam.developTheRocket();
        Rocket rocket = constructionTeam.constructRocket(plot);
        preparingTeam.prepareTheRocket(rocket);
        launchingTeam.launch(rocket);
    }
}
