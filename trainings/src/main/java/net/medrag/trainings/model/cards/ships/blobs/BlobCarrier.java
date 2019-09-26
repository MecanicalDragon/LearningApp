package net.medrag.trainings.model.cards.ships.blobs;

import net.medrag.trainings.model.Constraits;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class BlobCarrier extends BlobShip {

    public static int amount = 2;

    public String name = "Blob Carrier";
    public String url = "blob_carrier";
    public int price = 2;

    public BlobCarrier(){
        prime.put(Constraits.ATTACK, 3);
        ally.put(Constraits.ATTACK, 2);
    }

    @Override
    public String toString() {
        return "BlobCarrier{" +
                "prime=" + prime +
                ", ally=" + ally +
                ", util=" + util +
                '}';
    }
}
