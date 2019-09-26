package net.medrag.trainings.model.cards.ships;

import net.medrag.trainings.model.cards.Card;

import java.util.HashMap;
import java.util.Map;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public abstract class Ship extends Card {

    public int price;
    public String alliance;
    public String name;

    public Map<String, Integer>prime;
    public Map<String, Integer>ally;
    public Map<String, Integer>util;

    public Ship(){
        prime = new HashMap<>();
        ally = new HashMap<>();
        util = new HashMap<>();
    }

}
