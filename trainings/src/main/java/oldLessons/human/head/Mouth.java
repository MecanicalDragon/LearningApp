package oldLessons.human.head;

public class Mouth {
    String speech;
    int teethCount;

    public Mouth() {
    }


    public void talk(){
        System.out.println(speech);
    }

    @Override
    public String toString() {
        return "Mouth{" +
                "speech='" + speech + '\'' +
                ", teethCount=" + teethCount +
                '}';
    }
}
