package minigames.seaBattle.player;

import minigames.seaBattle.ShipDislocationException;
import minigames.seaBattle.placers.ShipPlacer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static minigames.seaBattle.Sea.LOCATIONS;
import static minigames.seaBattle.SeaBattle.*;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public abstract class AbstractPlayer {
    private final Map<Integer, Integer> yourFleet = new HashMap<>(DEFAULT_FLEET);

    private final char[][] yourFleetLocations = new char[10][10];

    private final String[] yourSea = new String[10];

    private void placeShipsRandomly(char[][] fleetLoc) {
        Random random = new Random();
        for (int i = 4, j = 1; i > 0; i--, j++) {
            for (int k = 0; k < j; k++) {
                boolean placed = false;
                while (!placed) {
                    int x = random.nextInt(11 - i);
                    int y = random.nextInt(11 - i);
                    ShipPlacer placer = i == 1 ? SINGLE_DECK_PLACER :
                            random.nextInt(10) % 2 == 0 ? HORIZONTAL_PLACER : VERTICAL_PLACER;
                    try {
                        placer.placeShip(x, y, i, fleetLoc);
                        placed = true;
                    } catch (ShipDislocationException ignored) {
                    }
                }
            }
        }
    }

    private void updateShipsDislocations() {
        for (int j = 0; j < yourFleetLocations.length; j++) {
            for (int i = 0; i < yourFleetLocations[j].length; i++) {
                if (yourFleetLocations[j][i] != '\u0000') {
                    yourSea[j] = new StringBuilder(yourSea[j]).replace(LOCATIONS[i], LOCATIONS[i] + 1, String.valueOf(SHIP)).toString();
                }
            }
        }
    }

    private void placeShips() {
        placeShipsRandomly(foesFleetLocations);
        if (randomMode) {
            placeShipsRandomly(yourFleetLocations);
            updateShipsDislocations();
            drawTheGrid();
            return;
        }
        while (!fleet.isEmpty()) {
            System.out.println("");
            System.out.println("PLACE YOUR SHIPS:");
            System.out.println(fleet);
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

                int letterIndex = Math.min(a, b) - START_LETTER;
                int digitIndex = Math.min(x, y);
                int size = a == b ? Math.abs(x - y) + 1 : Math.abs(a - b) + 1;

                Integer e = fleet.get(size);

                if (e != null && e > 0) {
                    placer.placeShip(letterIndex, digitIndex, size, yourFleetLocations);
                    reduceAvailableShips(size);
                    updateShipsDislocations();
                    drawTheGrid();
                } else System.out.println("There're no more ships of this type in yur stock.");

            } catch (ShipDislocationException e) {
                System.out.println("You can't place your ship this way!");
            } catch (Exception e) {
                System.out.println("Input ship locations according the following template: 'A0-D0', 'B2-B4'. Input ships one by one.");
//                e.printStackTrace();
            }
        }
    }

    private void reduceAvailableShips(int size) {
        Integer e = fleet.get(size) - 1;
        if (e <= 0) {
            fleet.remove(size);
        } else {
            fleet.put(size, e);
        }
    }
}
