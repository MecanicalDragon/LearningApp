package patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to do actions with object, not affecting it's business logic
 * Useful when:
 * > you need to apply an operation to all elements of some complex structure.
 * > you don't want to add excess business logic in elements
 * > new behaviour makes sense only for some classes of hierarchy
 * <p>
 * + simplifies adding of operations, that work with complex object structures
 * + unites relative operations in single class
 * + can stash state during structure elements iteration
 * <p>
 * - doesn't justify itself if structure changes often
 * - can break incapsulation of elements
 * <p>
 * IMPLEMENTATION
 * <p>
 * Создайте интерфейс посетителя и объявите в нём методы «посещения» для каждого класса элемента, который существует в программе.
 * <p>
 * Опишите интерфейс элементов. Если вы работаете с уже существующими классами, то объявите абстрактный метод принятия
 * посетителей в базовом классе иерархии элементов.
 * <p>
 * Реализуйте методы принятия во всех конкретных элементах. Они должны переадресовывать вызовы тому методу посетителя,
 * в котором тип параметра совпадает с текущим классом элемента.
 * <p>
 * Иерархия элементов должна знать только о базовом интерфейсе посетителей. С другой стороны, посетители будут знать
 * обо всех классах элементов.
 * <p>
 * Для каждого нового поведения создайте конкретный класс посетителя. Приспособьте это поведение для работы со всеми
 * типами элементов, реализовав все методы интерфейса посетителей.
 * <p>
 * Вы можете столкнуться с ситуацией, когда посетителю нужен будет доступ к приватным полям элементов. В этом случае
 * вы можете либо раскрыть доступ к этим полям, нарушив инкапсуляцию элементов, либо сделать класс посетителя вложенным
 * в класс элемента, если вам повезло писать на языке, который поддерживает вложенность классов.
 * <p>
 * Клиент будет создавать объекты посетителей, а затем передавать их элементам, используя метод принятия.
 */
public class Visitor {
}
