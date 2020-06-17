package minigames.cheeseRace;

class Cheese {

    private static double CHEESE_AMOUNT = 10;

    static double getRest() {
        return CHEESE_AMOUNT;
    }

    static boolean isExist() {
        return CHEESE_AMOUNT > 0;
    }

    static double eatCheese() {
        double cheeseAmount = Math.random();
        CHEESE_AMOUNT -= cheeseAmount;
        return cheeseAmount;
    }
}
