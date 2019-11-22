package oldLessons.human;

import oldLessons.human.context.Context;

public class Main {
    public static void main(String[] args) {
        Context context = new Context(Main.class.getResource("/old_lessons/human.xml").getPath());
        context.parseXML();
        try {
            context.instantiateParts();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | InstantiationException e) {
            e.printStackTrace();
        }

        for (Object o : context.autocreate()) {
            System.out.println(o);
            System.out.println("");
        }


    }
}
