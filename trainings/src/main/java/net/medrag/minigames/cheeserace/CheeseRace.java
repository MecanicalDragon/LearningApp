package net.medrag.minigames.cheeserace;

public class CheeseRace {
    public static void main(String[] args) {

        Mouse.init(6);
        Cat hunter = new Cat("Viktor", 0.6);

        System.out.println("In blue corner there is a cat, named " + hunter.name + "!");
        System.out.println("In red corner there are " + Mouse.NEST.size() + " mice!");
        System.out.println("Quantity of cheese is " + Cheese.getRest() + ".");
        System.out.println("Cheese race starts!");

        while (Cheese.isExist() && Mouse.NEST.size() > 0) {
            Mouse mouse = Mouse.NEST.get(0);
            mouse.stealCheese();
            hunter.huntMouse(mouse);
        }

        if (Cheese.isExist()) {
            System.out.println(String.format("%s has caught all the mice! %s total. %.2f of cheese left.",
                    hunter.name, hunter.mouseEaten, Cheese.getRest()));
        } else {
            System.out.println(String.format("Whole cheese has been eaten. %s has caught %s mice, " +
                            "but that wasn't enough and he'd been kicked out of the house. Nest counts %s mice.",
                    hunter.name, hunter.mouseEaten, Mouse.NEST.size()));
        }
    }
}
