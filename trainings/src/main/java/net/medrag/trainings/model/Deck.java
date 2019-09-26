package net.medrag.trainings.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public abstract class Deck {

    public static final List<String> deck = new ArrayList<>();

    static{
        deck.add("net.medrag.trainings.model.cards.ships.blobs.BlobCarrier");
        deck.add("net.medrag.trainings.model.cards.ships.blobs.BlobFighter");
        deck.add("net.medrag.trainings.model.cards.ships.empire.EmpireCarrier");
        deck.add("net.medrag.trainings.model.cards.ships.empire.EmpireFighter");
    }

}
