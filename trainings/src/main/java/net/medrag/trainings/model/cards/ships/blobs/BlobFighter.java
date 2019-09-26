package net.medrag.trainings.model.cards.ships.blobs;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class BlobFighter extends BlobShip {

    public static int amount = 4;

    public String name = "Blob Fighter";
    public String url = "blob_fighter";
    public int price = 1;

    public BlobFighter(){
        prime.put(Constraits.ATTACK, 1);
        ally.put(Constraits.ATTACK, 2);
        util.put(Constraits.ATTACK, 3);
    }

    @Override
    public String toString() {
        return "BlobFighter{" +
                "prime=" + prime +
                ", ally=" + ally +
                ", util=" + util +
                '}';
    }
}
