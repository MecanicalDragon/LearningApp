package net.medrag.spring.application.service.api;

import net.medrag.spring.application.domain.Plot;
import net.medrag.spring.application.domain.Rocket;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public interface ConstructionTeam {
    Rocket constructRocket(Plot plot);
}
