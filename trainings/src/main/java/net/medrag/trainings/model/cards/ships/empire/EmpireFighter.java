package net.medrag.trainings.model.cards.ships.empire;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class EmpireFighter extends EmpireShip {

    public static int amount = 3;

    public String name = "Empire Fighter";
    public String url = "empire_fighter";
    public int price = 1;

    public EmpireFighter(){
        prime.put(Constraits.ATTACK, 2);
        prime.put(Constraits.DRAW, 1);
        ally.put(Constraits.DISCARD, 1);
    }

    @Override
    public String toString() {
        return "EmpireFighter{" +
                "prime=" + prime +
                ", ally=" + ally +
                ", util=" + util +
                '}';
    }
}
