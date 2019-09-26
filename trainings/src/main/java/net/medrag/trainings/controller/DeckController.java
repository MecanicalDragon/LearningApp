package net.medrag.trainings.controller;

import net.medrag.trainings.model.cards.StartHand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.medrag.trainings.model.DrawPile;
import net.medrag.trainings.model.cards.Card;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 08.11.2018
 */
@RestController
public class DeckController {

    private static final Logger LOGGER = LogManager.getLogger(DeckController.class);

    @GetMapping("/getNewDrawPile")
    public List<Card> getNewDeck(HttpServletRequest request) throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        DrawPile drawPile = new DrawPile();
        List<Card> trade = new ArrayList<>();
        LOGGER.info("Drawpile size before handing: " + drawPile.getDeck().size());
        for (int i = 0; i < 5; i++) {
            trade.add(drawPile.getDeck().remove(0));
        }
        LOGGER.info("Drawpile size after handing: " + drawPile.getDeck().size());
        request.getSession().setAttribute("drawpile", drawPile.getDeck());
        return trade;
    }

    @GetMapping("/getStartPile")
    public List<Card> getStartPile(@RequestParam String player, HttpServletRequest request){
        List<Card> startPile = new ArrayList<>(new StartHand().getHand());
        List<Card> startHand = new ArrayList<>(startPile.subList(0,5));
        for (int i = 0; i < 5; i++) {
        startPile.remove(0);
        }
        LOGGER.info("Start Pile Distribution for player "+player);
        request.getSession().setAttribute("startPile"+player, startPile);
        return startHand;
    }

}
