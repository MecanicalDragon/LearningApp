package net.medrag.trainings.model;

import net.medrag.trainings.model.cards.Card;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 07.11.2018
 */
public class DrawPile extends Deck{

    private List<Card> deck;

    public DrawPile() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        deck = new ArrayList<>();
        for (String ship :  super.deck){
            Class<?> clazz = Class.forName(ship);
            Field a = clazz.getField("amount");
            int amount = a.getInt(clazz);
            for (int i = 0; i < amount; i++) {
            Card c = (Card)clazz.newInstance();
            deck.add(c);
            }
        }
        Collections.shuffle(deck);
    }

    public List<Card> getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        return "DrawPile{" +
                "deck=" + deck +
                '}';
    }
}
