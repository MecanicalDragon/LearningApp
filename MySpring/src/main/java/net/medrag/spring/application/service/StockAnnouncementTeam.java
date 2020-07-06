package net.medrag.spring.application.service;

import net.medrag.spring.application.service.api.AnnouncementTeam;

/**
 * @author Stanislav Tretyakov
 * 03.07.2020
 */
public class StockAnnouncementTeam implements AnnouncementTeam {
    @Override
    public void announce(String announce) {
        System.out.println(announce);
    }
}
