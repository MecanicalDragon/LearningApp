package oldLessons.miceGame;

class Cat {

     String name;
     int mouseEaten;
    private double successfulHuntChance;

    Cat(String name, double chance) {
        this.name = name;
        successfulHuntChance = chance;
    }

    void huntMouse(Mouse mouse) {
        double d = Math.random();
        if (d < successfulHuntChance) {
            System.out.println(String.format("%s catched a mouse!", name));
            mouse.beEaten();
            mouseEaten++;
        }
    }

}
