package net.medrag.trainings.model.cards;

import net.medrag.trainings.model.Deck;
import net.medrag.trainings.model.cards.ships.Attacker;
import net.medrag.trainings.model.cards.ships.Trader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 09.11.2018
 */
public class StartHand extends Deck {

    private List<Card> hand;

    public StartHand(){
        hand = new ArrayList<>();
        for (int i = 0; i < Trader.amount/2; i++) {
            hand.add(new Trader());
        }
        for (int i = 0; i < Attacker.amount / 2; i++) {
            hand.add(new Attacker());
        }
        Collections.shuffle(hand);
    }

    public List<Card> getHand() {
        return hand;
    }
}
