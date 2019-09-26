package net.medrag.trainings.model.cards.ships;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 09.11.2018
 */
public class Pirate extends Ship {

    public static int amount = 10;

    public String name = "Pirate";
    public String url = "pirate";
    public int price = 2;
    public String alliance = Constraits.NEUTRAL;

    public Pirate(){
        prime.put(Constraits.CASH, 2);
        util.put(Constraits.ATTACK, 2);

    }
}
