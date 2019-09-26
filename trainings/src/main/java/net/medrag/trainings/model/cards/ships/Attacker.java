package net.medrag.trainings.model.cards.ships;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class Attacker extends Ship {

    public static int amount = 4;

    public String name = "Attacker";
    public String url = "attacker";
    public int price = 1;
    public String alliance = Constraits.STARTER;

    public Attacker(){
        prime.put(Constraits.ATTACK, 1);
    }
}
