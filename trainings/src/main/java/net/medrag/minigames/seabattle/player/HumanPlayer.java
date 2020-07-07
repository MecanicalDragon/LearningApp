package net.medrag.minigames.seabattle.player;

import net.medrag.minigames.seabattle.battle.ShipDislocationException;
import net.medrag.minigames.seabattle.battle.DigitIndex;
import net.medrag.minigames.seabattle.battle.LetterIndex;
import net.medrag.minigames.seabattle.battle.Shot;
import net.medrag.minigames.seabattle.placers.ShipPlacer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static net.medrag.minigames.seabattle.placers.HorizontalShipPlacer.HORIZONTAL_PLACER;
import static net.medrag.minigames.seabattle.placers.SingleDeckShipPlacer.SINGLE_DECK_PLACER;
import static net.medrag.minigames.seabattle.placers.VerticalShipPlacer.VERTICAL_PLACER;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public class HumanPlayer extends Player {

    private final static String SHIP_REGEX = "^[A-J][0-9]-[A-J][0-9]$";
    private final static String SHOT_REGEX = "^[A-J][0-9]$";
    private final static Scanner SCANNER = new Scanner(System.in);

    private final Map<Integer, Integer> fleetSupply = new HashMap<>(DEFAULT_FLEET);

    @Override
    public void placeShips() {
        System.out.println("Do you want to place your ships randomly?");
        System.out.println("(Input anything, if you do)");
        if (!SCANNER.nextLine().isEmpty()) {
            placeShipsRandomly();
            updateShipsDislocations();
            battleController.drawTheGrid();
            return;
        }
        while (!fleetSupply.isEmpty()) {
            System.out.println("");
            System.out.println("PLACE YOUR SHIPS:");
            System.out.println(fleetSupply);
            String ship = SCANNER.nextLine().toUpperCase();
            try {
                if (!ship.matches(SHIP_REGEX) && !ship.matches(SHOT_REGEX)) {
                    throw new Exception();
                }
                boolean oneDeckShip = ship.length() == 2;
                char a = ship.charAt(0);
                char b = oneDeckShip ? ship.charAt(0) : ship.charAt(3);
                int x = Integer.parseInt(String.valueOf(ship.charAt(1)));
                int y = Integer.parseInt(String.valueOf(oneDeckShip ? ship.charAt(1) : ship.charAt(4)));

                ShipPlacer placer = oneDeckShip ? SINGLE_DECK_PLACER : a == b ? HORIZONTAL_PLACER : VERTICAL_PLACER;

                int letterIndex = Math.min(a, b) - LetterIndex.CHAR_SHIFT;
                int digitIndex = Math.min(x, y);
                int size = a == b ? Math.abs(x - y) + 1 : Math.abs(a - b) + 1;

                Integer e = fleetSupply.get(size);

                if (e != null && e > 0) {
                    placer.placeShip(letterIndex, digitIndex, size, fleetLocations);
                    reduceAvailableShips(size);
                    updateShipsDislocations();
                    battleController.drawTheGrid();
                } else System.out.println("There're no ships of this type in your stock.");

            } catch (ShipDislocationException e) {
                System.out.println("You can't place your ship this way!");
            } catch (Exception e) {
                System.out.println("Input ship locations according the following template: 'A0-D0', 'B2-B4'.\n" +
                        "Or just 'J9', 'H7' for single-deck ships. Input ships one by one.");
            }
        }
    }

    @Override
    public Shot makeTheShot() {
        System.out.println("Specify shooting sector:");
        while (true) {
            String target = SCANNER.nextLine().toUpperCase();
            if (target.matches(SHOT_REGEX)) {
                LetterIndex letterIndex = LetterIndex.valueOf(target.substring(0, 1));
                DigitIndex digitIndex = DigitIndex.values()[Integer.parseInt(target.substring(1, 2))];
                Shot shot = new Shot(letterIndex, digitIndex);
                battleController.addMessage("You shoot at " + shot);
                return shot;
            } else System.out.println("Specify target area matching following pattern: 'A0'.");
        }
    }

    @Override
    protected void updateShipsDislocations() {
        for (int j = 0; j < fleetLocations.length; j++) {
            for (int i = 0; i < fleetLocations[j].length; i++) {
                if (fleetLocations[j][i] != '\u0000') {
                    sea[j] = new StringBuilder(sea[j]).replace(LOCATIONS[i], LOCATIONS[i] + 1,
                            String.valueOf(fleetLocations[j][i])).toString();
                }
            }
        }
    }

    private void reduceAvailableShips(int size) {
        Integer e = fleetSupply.get(size) - 1;
        if (e <= 0) {
            fleetSupply.remove(size);
        } else {
            fleetSupply.put(size, e);
        }
    }
}
