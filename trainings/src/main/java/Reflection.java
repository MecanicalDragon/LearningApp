import java.util.ArrayList;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 14.11.2019
 */
public class Reflection {
    public static void main(String[] args) {



    }

    static class Ancestor{
        String race;
    }

    static class Dec1 extends Ancestor{
        Integer age;
    }

    static class Dec2 extends Ancestor{
        List<String> list;
    }

    static <I> I reflect(I in){
        System.out.println(in.getClass());
        System.out.println(Dec1.class);
        return null;
    }
}
