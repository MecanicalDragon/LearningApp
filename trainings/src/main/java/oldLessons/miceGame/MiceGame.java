package oldLessons.miceGame;

public class MiceGame {
    public static void main(String[] args) {

        Mouse.init(6);
        Cat hunter = new Cat("Viktor", 0.6);

        while (Cheese.isExist() && Mouse.NEST.size() > 0) {
            Mouse mouse = Mouse.NEST.get(0);
            mouse.stealCheese();
            hunter.huntMouse(mouse);
        }

        if (Cheese.isExist()) {
            System.out.println(String.format("%s catched all mice! %s total. %.2f of cheese left.",
                    hunter.name, hunter.mouseEaten, Cheese.getRest()));
        } else {
            System.out.println(String.format("All cheese was eaten. %s catched %s mice, but that wasn't enough," +
                            " and he was kick out of the house. Nest counts %s mice",
                    hunter.name, hunter.mouseEaten, Mouse.NEST.size()));
        }
    }
}
