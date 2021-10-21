package something;

/**
 * WHY STATIC METHODS ARE BAD?
 * <p>
 * Object-orientation is about three things:
 * <p>
 * messaging,
 * local retention and protection and hiding of state-process, and
 * extreme late-binding of all things.
 * Of those three, the most important one is messaging.
 * <p>
 * Static methods violate at least messaging and late-binding.
 * <p>
 * The idea of messaging means that in OO, computation is performed by networks of self-contained objects which send
 * messages to each other. Sending a message is the only way of communication/computation.
 * <p>
 * Static methods don't do that. They aren't associated with any object. They really aren't methods at all, according
 * to the usual definition. They are really just procedures. There's pretty much no difference between a Java static
 * method Foo.bar and a BASIC subroutine FOO_BAR.
 * <p>
 * As for late-binding: a more modern name for that is dynamic dispatch. Static methods violate that, too, in fact,
 * it's even in their very name: static methods.
 * <p>
 * Static methods break some very nice properties of object-orientation. For example, object-oriented systems are
 * automatically capability-safe with objects acting as capabilities. Static methods (or really any statics, be that
 * static state or static methods) break that property.
 * <p>
 * You can also execute every object in parallel in its own process, since they only communicate via messaging, thus
 * providing some trivial concurrency. (Like Actors, basically, which shouldn't be too surprising, since Carl Hewitt
 * created the Actor Model based on Smalltalk-71, and Alan Kay created Smalltalk-71 partially based on PLANNER, which
 * in turn was created by Carl Hewitt. The close relationship between actors and objects is far from coincidental, in
 * fact, they are essentially one and the same.) Again, statics (both static methods, and especially static state)
 * break that nice property.
 *
 * @author Stanislav Tretyakov
 * 14.03.2021
 */
public class CalculatorPlus
//        extends Calculator
{

    /**
     * 1. Static method doesn't support inheritance (doesn't allow to override it).
     * 2. CompilationError because method is final in calculator, so can not be hidden.
     * 3. Integer instead of int can produce OutOfMemoryError
     * 4. Recursive call can produce StackOverflowError
     * 5. If y is negative, method will return wrong result.
     * 6. What about unboxing of y?
     */
    public static Integer multiply(Integer x, Integer y) {
        return (y == 0) ? 0 : multiply(x, y - 1) + x;
    }
}
