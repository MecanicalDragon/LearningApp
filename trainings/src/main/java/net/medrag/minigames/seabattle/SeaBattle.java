package net.medrag.minigames.seabattle;

import lombok.SneakyThrows;
import net.medrag.minigames.seabattle.battle.ShotResult;
import net.medrag.minigames.seabattle.player.*;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Stanislav Tretyakov
 * 04.07.2020
 */
public class SeaBattle {
    private final static String HEAD1 = "P1 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |";
    private final static String HEAD2 = "P2 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |";
    private final static String SEP = "---+---+---+---+---+---+---+---+---+---+---+";
    private final static String STATS_TEMPLATE = "                                                        ";
    private final static String LAND = "            ";
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static int SIZE = 10;
    private static boolean resumeGame = true;

    private final Player player1;
    private final Player player2;

    private Queue<String> announcements = new ArrayDeque<>();
    private boolean battleStarted;

    private SeaBattle(Player player1, Player player2) {
        this.player1 = player1;
        player1.setBattleController(this);
        this.player2 = player2;
        player2.setBattleController(this);
    }

    public static void main(String[] args) {
        System.out.println("WELCOME TO THE SEA BATTLE!");
        while (resumeGame) {
            Player player1 = selectPlayer("INPUT TO SELECT PLAYER ONE:");
            Player player2 = selectPlayer("INPUT TO SELECT PLAYER TWO:");
            SeaBattle seaBattle = new SeaBattle(player1, player2);
            seaBattle.drawTheGrid();

            player1.placeShips();
            player2.placeShips();

            seaBattle.fight();
            announceTheEnd();
        }
        System.out.println("BYE AND COME BACK AGAIN!");
    }

    private static void announceTheEnd() {
        System.out.println();
        System.out.println("Would you like to play once more?");
        String s = SCANNER.nextLine();
        if (!s.toLowerCase().equals("yes")) resumeGame = false;
    }

    private static Player selectPlayer(String announce) {
        System.out.println(announce);
        System.out.println("1 - Human player");
        System.out.println("2 - Dummy Bot");
        System.out.println("3 - Simple Bot");
        System.out.println("4 - Smart Bot");
        System.out.println("5 - Clever Bot");
        String s = SCANNER.nextLine();
        switch (s) {
            case "1":
                return new HumanPlayer();
            case "3":
                return new SimpleBot();
            case "4":
                return new SmartBot();
            case "5":
                return new CleverBot();
            default:
                return new DummyBot();
        }
    }

    private void fight() {
        System.out.println("LET THE BATTLE BEGIN!");
        battleStarted = true;
        game:
        while (true) {
            if (!player1.isDefeated()) {
                while (true) {
                    ShotResult shotResult = player1.makeMove(player2);
                    drawTheGrid();
                    if (player2.isDefeated()) {
                        break game;
                    }
                    if (shotResult == ShotResult.MISS) break;
                }
            }
            if (!player2.isDefeated()) {
                while (true) {
                    ShotResult shotResult = player2.makeMove(player1);
                    drawTheGrid();
                    if (player1.isDefeated()) {
                        break game;
                    }
                    if (shotResult == ShotResult.MISS) break;
                }
            }
        }
        finishTheGame();
    }

    private void finishTheGame() {
        String player = player1.isDefeated() ? "Player 2" : "Player 1";
        System.out.println(player + " win! Congratulations!");
    }

    @SneakyThrows
    public void drawTheGrid() {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println();
        System.out.println(HEAD1 + LAND + HEAD2);
        System.out.println(SEP + LAND + SEP);

        for (int i = 0; i < SIZE; i++) {
            System.out.println(player1.getSeaRow(i) + LAND + player2.getSeaRow(i));
            System.out.println(SEP + LAND + SEP);
        }

        if (battleStarted) {
            String fleet1 = player1.printFleet();
            StringBuilder sb = new StringBuilder(STATS_TEMPLATE);
            sb.replace(0, fleet1.length(), fleet1);
            sb.append(player2.printFleet());
            System.out.println(sb.toString());
        }

        System.out.println();
        while (!announcements.isEmpty()) {
            System.out.println(announcements.poll());
        }
    }

    public void addMessage(String message) {
        announcements.add(message);
    }
}
