package oldLessons.genericsAndComparators;

import java.io.Serializable;

// Generics can't be static.
// Generics can't be used in throwable classes.
// Generics are generics only before compilation -
// After compilation all generics become Objects.
// That's why you can't use methods with equal signatures for different generics
// in the same class.
// Else you can't create generics wih keyword "new" because of it.

public class Generic<T extends Number & Serializable> {
    private T generic;
    private T generic2;
    private T generic3;

    Generic(T generic){
        this.generic = generic;
    }

    public void setGeneric2(T g){
        generic2 = g;
    }

    public void setGeneric3 (T generic){
        this.generic3 = generic;
    }

    public void show(){
        System.out.println(generic.doubleValue());
        System.out.println(generic2.doubleValue());
        System.out.println(generic3.doubleValue());
    }


}
