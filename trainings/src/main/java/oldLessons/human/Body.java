package oldLessons.human;

import oldLessons.human.context.AutoCreate;

@AutoCreate(true)
public class Body {
    enum Constitution{
        STRONG,
        ATHLETIC,
        WEAK,
        FAT
    }

    Constitution constitution;
    String tattoo;

    public Body() {
    }

    @Override
    public String toString() {
        return "Body{" +
                "constitution=" + constitution +
                ", tattoo='" + tattoo + '\'' +
                '}';
    }
}
