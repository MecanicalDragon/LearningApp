package oldLessons.genericsAndComparators;

public class GenQ<T extends Number> {
    private T operand;

    GenQ(T value){
        this.operand = value;
    }

    // This method can receive only that type, what been initialized in constructor.

    void showOnlyThisTypeDelta(GenQ<T> gen){
        System.out.println(this.operand.doubleValue() - gen.operand.doubleValue());
    }

    /* Question char allows to use in this method not only that type, what been
    initialized in constructor, but all other types, extended from Number.
      */
    void showDelta(GenQ<?> gen){
        System.out.println(this.operand.doubleValue() - gen.operand.doubleValue());
    }

    /* But if you write like this, you can add in this method only Doubles
    *  Same time, you can't receive entirely other type like String - it still must
    *  be extended from Number.
    * */
    void showDoubleDelta(GenQ<?> gen){
        System.out.println(this.operand.doubleValue() - gen.operand.doubleValue());
    }

    // This method receives only superclasses of Integer, but still extended from Number.
    void showSuperDelta(GenQ<? super Integer> gen){
        System.out.println(this.operand.doubleValue() - gen.operand.doubleValue());
    }
}
