package oldLessons.multithreading;

public class Main4 {

    public static void main(String[] args) {
//        AtomicInteger ai = new AtomicInteger(5);
//        AtomicInteger ad = new AtomicInteger(10);
        Object o = new Object();
        AbstractClass a = new Abss("ololo");
//        while (x>0){
//            x+=10000;
//
//        }
////        assert x>0 : x;  // "-ea" to the Run -> Edit Configurations -> VM options
//        System.out.println(ai.toString());
//        System.out.println(ad.toString());
//        System.out.println(o.toString());
//        System.out.println(a.toString());
        System.out.println(a.i);
        Abss b = (Abss)a;
        b.print();
        int x = 0;
        System.out.println(x++==++x);
        Double d1 = 1d;
        Double d2 = 1d;
        System.out.println(d1==d2);




    }
}
