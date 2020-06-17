package minigames.cheeseRace;

import java.util.ArrayList;
import java.util.List;

class Mouse {

    static final List<Mouse> NEST = new ArrayList<>();
    private static final double POPULATION_INCREASE_REQUIREMENT = 2.0;
    private static double food;

    static void init(int amount){
        for (int i = 0; i < amount; i++) {
            NEST.add(new Mouse());
        }
    }

    private static void bornNewMouse(){
        NEST.add(new Mouse());
    }

    void stealCheese(){
        double stolen = Cheese.eatCheese();
        System.out.println(String.format("Mice have stolen %.2f of cheese. %.2f left.", stolen, Cheese.getRest()));
        food += stolen;
        if(food > POPULATION_INCREASE_REQUIREMENT){
            bornNewMouse();
            System.out.println("Mice population increased! Now there are " + NEST.size() + " mice.");
            food -= POPULATION_INCREASE_REQUIREMENT;
        }
    }

    void beEaten(){
        NEST.remove(this);
    }

}
