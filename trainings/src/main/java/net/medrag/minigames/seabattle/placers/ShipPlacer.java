package net.medrag.minigames.seabattle.placers;

import net.medrag.minigames.seabattle.ShipDislocationException;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public interface ShipPlacer {
    void placeShip(int letterIndex, int digitIndex, int size, char[][] sea) throws ShipDislocationException;
}