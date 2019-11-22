package oldLessons.genericsAndComparators;

public class MethGen {

    public  <K extends String, V extends Integer> void show(K k, V v){
        System.out.println(k+" & "+v.toString());
    }
}

// Example of generic method
