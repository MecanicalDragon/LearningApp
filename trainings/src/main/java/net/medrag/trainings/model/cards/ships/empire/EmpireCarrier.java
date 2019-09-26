package net.medrag.trainings.model.cards.ships.empire;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class EmpireCarrier extends EmpireShip {

    public static int amount = 3;

    public String name = "Empire Carrier";
    public String url = "empire_carrier";
    public int price = 2;

    public EmpireCarrier(){
        prime.put(Constraits.ATTACK, 2);
        prime.put(Constraits.DISCARD, 1);
        ally.put(Constraits.DISCARD, 1);
    }

    @Override
    public String toString() {
        return "EmpireCarrier{" +
                "prime=" + prime +
                ", ally=" + ally +
                ", util=" + util +
                '}';
    }
}
