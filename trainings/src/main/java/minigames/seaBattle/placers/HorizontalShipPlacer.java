package minigames.seaBattle.placers;

import minigames.seaBattle.ShipDislocationException;

import static minigames.seaBattle.SeaBattle.SHIP;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public class HorizontalShipPlacer implements ShipPlacer {
    @Override
    public void placeShip(int letterIndex, int digitIndex, int size, char[][] sea) throws ShipDislocationException {
        for (int i = digitIndex - 1; i < digitIndex + size + 1; i++) {
            if (i < 0 || i > 9) continue;
            if (sea[letterIndex][i] == SHIP) throw new ShipDislocationException();

            int y1 = letterIndex - 1;
            if (y1 >= 0) {
                if (sea[y1][i] == SHIP) throw new ShipDislocationException();
            }
            int y2 = letterIndex + 1;
            if (y2 <= 9) {
                if (sea[y2][i] == SHIP) throw new ShipDislocationException();
            }
        }

        for (int i = digitIndex; i < digitIndex + size; i++) {
            sea[letterIndex][i] = SHIP;
        }
    }
}
