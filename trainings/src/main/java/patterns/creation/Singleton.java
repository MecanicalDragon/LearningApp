package patterns.creation;

/**
 * {@author} Stanislav Tretyakov
 * 28.01.2020
 * <p>
 * > use it when you need single instance of object(for database access, for ex.)
 * <p>
 * - pattern breaks Single Responsibility principle
 * - pattern has multithreading problems
 * - pattern requires mocks during unit testing
 * <p>
 * IMPLEMENTATION
 * <p>
 * Добавьте в класс приватное статическое поле, которое будет содержать одиночный объект.
 * <p>
 * Объявите статический создающий метод, который будет использоваться для получения одиночки.
 * <p>
 * Добавьте «ленивую инициализацию» (создание объекта при первом вызове метода) в создающий метод одиночки.
 * <p>
 * Сделайте конструктор класса приватным.
 * <p>
 * В клиентском коде замените вызовы конструктора одиночка вызовами его создающего метода.
 */
public class Singleton {
    private static volatile Singleton instance;
    private String value;

    private Singleton(String value) {
        this.value = value;
    }

    public void printValue() {
        System.out.println(value);
    }

    public static Singleton getInstance(String val) {

        Singleton result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton(val);
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton singleton = getInstance("first");
        singleton.printValue();
        Singleton second = getInstance("second");
        second.printValue();
    }
}
