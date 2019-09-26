package net.medrag.trainings.model.cards.ships;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class Trader extends Ship {

    public static int amount = 16;

    public String name = "Trader";
    public String url = "trader";
    public int price = 1;
    public String alliance = Constraits.STARTER;

    public Trader(){
        prime.put(Constraits.CASH, 1);
    }

}
