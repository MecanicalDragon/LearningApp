package net.medrag.minigames;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * {@author} Stanislav Tretyakov
 * 02.12.2019
 */
public class FleaRun {

    static final class Flea {
        String name;
        int initiative;
        int speed;
        int stamina;
        StringBuilder distance = new StringBuilder();
        boolean finished;

        void jump() {
            int jumpLength = stamina-- > 0 ? speed : speed / 2;
            if (jumpLength == 0) jumpLength = 1;
            StringBuilder jump = new StringBuilder();
            for (int j = 0; j < jumpLength; j++) {
                jump.append("=");
            }
            distance.insert(18, jump);
            for (int j = 0; j < jump.length(); j++) {
                if (distance.lastIndexOf(" ") > 18) {
                    distance.deleteCharAt(distance.lastIndexOf(" "));
                } else {
                    distance.replace(18 + DISTANCE, 18 + DISTANCE + 20, "|>");
                    break;
                }
            }
        }

        boolean isFinished() {
            return distance.lastIndexOf(">") >= 18 + DISTANCE;
        }

        @Override
        public String toString() {
            return "Flea{" +
                    "name='" + name + '\'' +
                    ", initiative=" + initiative +
                    ", speed=" + speed +
                    ", stamina=" + stamina +
                    '}';
        }
    }

    private static final String[] NAMES = {"Boris", "Alfonse", "Antony", "Susanna", "George", "Lucy", "Konstantin",
            "Beatrisa", "Francois", "Napoleon", "Benny", "Alfred", "Michelle", "Emanuele", "Amelia", "Morty", "Luna",
            "Gregory", "Maxim", "Sally", "Sue", "Betty", "Bjorn", "Agnesse", "Victor", "Vladimir", "Einstein", "Nobel",
            "Bartolomeo", "Angus", "Sebastian", "Francesca", "Cratos", "Zeus", "Gargantua", "Sherlock", "Selestina"};
    private static Flea[] PARK = new Flea[4];
    private static int DISTANCE = 50;
    private static int TURN_DURATION = 1000;
    private static String TRACK = defineTrack();

    private static String defineTrack() {
        StringBuilder track = new StringBuilder();
        for (int i = 0; i < DISTANCE; i++) track.append(" ");
        return track.replace(DISTANCE - 1, DISTANCE + 20, "|").toString();
    }

    private static void applyArgs(String[] args) {
        if (args.length > 0) {
            try {
                int park = PARK.length;
                int distance = DISTANCE;
                int turnDuration = TURN_DURATION;
                boolean recalculateTrack = false;
                switch (args.length) {
                    case 3:
                        park = Integer.parseInt(args[2]);
                        if (park < 3 || park > 16) park = PARK.length;
                    case 2:
                        distance = Integer.parseInt(args[1]);
                        if (distance < 10 || distance > 100) distance = DISTANCE;
                        else recalculateTrack = true;
                    case 1:
                        turnDuration = Integer.parseInt(args[0]);
                        if (turnDuration < 10 || turnDuration > 10000) turnDuration = TURN_DURATION;
                        break;
                }

                PARK = new Flea[park];
                DISTANCE = distance;
                TURN_DURATION = turnDuration;
                if (recalculateTrack) TRACK = defineTrack();

            } catch (Exception e) {
                System.out.println("One or more of init parameters is/are incorrect. Default params were applied.");
            }
        }
    }

    public static void main(String[] args) {

        applyArgs(args);
        createFlea();
        createOpponents();
        startTheRun();

    }

    private static void startTheRun() {
        String first = null;
        String second = null;
        String third = null;
        try {
            System.err.println("LET THE RACE BEGIN!!!");
            sleep(10);
            for (Flea f : PARK) {
                f.distance.append(f.name);
                for (int j = f.name.length(); j < 16; j++) {
                    f.distance.append(" ");
                }
                f.distance.append(":|>").append(TRACK);
                System.out.println(f.distance);
            }

            int finished = 0;
            do {
                boolean movePerformed = false;
                for (Flea f : PARK) {
                    if (!f.finished) {
                        int initiative = (int) (Math.ceil(Math.random() * 10));
                        int currentTurnInitiative = f.stamina > 0 ? f.initiative + 3 : f.initiative / 2 + 3;
                        if (initiative <= (currentTurnInitiative)) {
                            f.jump();
                            movePerformed = true;
                            if (f.isFinished()) {
                                f.finished = true;
                                finished++;

                                //  Define prize places
                                if (first == null) {
                                    first = f.name;
                                } else {
                                    if (second == null) {
                                        second = f.name;
                                    } else {
                                        if (third == null) {
                                            third = f.name;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!movePerformed) continue;
                sleep(TURN_DURATION);
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                System.out.println("");
                for (Flea flea : PARK) {
                    System.out.println(flea.distance);
                }
            } while (finished < PARK.length);

            System.out.println("");
            sleep(10);
            System.err.println("THE RACE IS OVER!!!");
            sleep(10);
            System.out.println("First place: " + first);
            System.out.println("Second place: " + second);
            System.out.println("Third place: " + third);

        } catch (Exception ignored) {
        }
    }

    private static void createOpponents() {
        String[] names = new String[PARK.length + 1];
        names[0] = PARK[0].name;
        for (int i = 1; i < PARK.length; i++) {
            Flea f = new Flea();
            boolean newName = false;
            label:
            do {
                f.name = NAMES[(int) (Math.random() * NAMES.length)];
                for (String n : names) {
                    if (n == null) break label;
                    if (n.equals(f.name)) {
                        newName = true;
                        break;
                    }
                }
            } while (newName);
            names[i] = f.name;
            f.initiative = (int) (Math.ceil(Math.random() * 4 + 3));
            f.speed = (int) (Math.ceil(Math.random() * 4 + 3));
            f.stamina = 15 - f.initiative - f.speed;
            PARK[i] = f;
        }
        System.out.println("Your foes:");
        for (int i = 1; i < PARK.length; i++) {
            System.out.println(PARK[i]);
        }
        Arrays.sort(PARK, (x, y) -> y.initiative - x.initiative);
        System.out.println("Press Enter to start the race.");
        new Scanner(System.in).nextLine();
    }

    private static void createFlea() {

        System.err.println("WELCOME TO THE FLEA RUNS!!!");
        Flea flea = new Flea();
        Scanner sc = new Scanner(System.in);
        int points = 15;

        //  input name
        String name;
        do {
            System.out.println("Enter your flea's name (3 - 16 characters):");
            name = sc.nextLine();
            if (name.length() < 3 || name.length() > 16) System.err.println("MORE 3, LESS 16 CHARACTERS!!!");
        } while (name.length() < 3 || name.length() > 16);
        flea.name = name;
        System.out.println("Your flea's name is " + name);

        //  just text
        System.out.println("Now you have " + points + " points to divide it among 3 characteristics of your flea:");
        System.out.println("Initiative - frequency of your flea's jumps.");
        System.out.println("Speed - distance of jump.");
        System.out.println("Stamina - power reserve.");
        System.out.println("Spread it wisely...");

        //  input initiative
        boolean initiativeRequired = true;
        do {
            try {
                System.out.println("Input initiative. Min is 1, max is 7.");
                int initiative = Integer.parseInt(sc.nextLine());
                if (initiative < 1) initiative = 1;
                if (initiative > 7) initiative = 7;
                points -= initiative;
                flea.initiative = initiative;
                System.out.println("Your flea's initiative has been set to " + initiative);
                System.out.println("You have " + points + " points now.");
                initiativeRequired = false;
            } catch (NumberFormatException e) {
                System.err.println("INPUT A DIGIT!!!");
            }
        } while (initiativeRequired);

        //  input speed
        boolean speedRequired = true;
        do {
            try {
                System.out.println("Input speed. Min is 1, max is 7.");
                int speed = Integer.parseInt(sc.nextLine());
                if (speed < 1) speed = 1;
                if (speed > 7) speed = 7;
                points -= speed;
                flea.speed = speed;
                System.out.println("Your flea's speed has been set to " + speed);
                speedRequired = false;
            } catch (NumberFormatException e) {
                System.err.println("INPUT A DIGIT!!!");
            }
        } while (speedRequired);

        //  calculate stamina
        if (points > 7) points = 7;
        System.out.println("Your flea's stamina has been set to " + points);
        flea.stamina = points;
        System.out.println("Your flea:");
        System.out.println(flea);
        PARK[0] = flea;
    }
}
