package patterns.structure;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to divide class to abstraction and implementation, and change it independently. It's useful when:
 * > you want to separate monolith class, that contains different functionality implementations.
 * > you need to extend class in different ways.
 * > you want to change implementation during runtime
 * <p>
 * + allows to build platform-independent programs
 * + hides excess details from client
 * + open-closed principle implementation
 * <p>
 * - complicates program code
 * <p>
 * IMPLEMENTATION
 * <p>
 * Определите, существует ли в ваших классах два непересекающихся измерения. Это может быть функциональность/платформа,
 * предметная-область/инфраструктура, фронт-энд/бэк-энд или интерфейс/реализация.
 * <p>
 * Продумайте, какие операции будут нужны клиентам, и опишите их в базовом классе абстракции.
 * <p>
 * Определите поведения, доступные на всех платформах, и выделите из них ту часть, которая нужна абстракции.
 * На основании этого опишите общий интерфейс реализации.
 * <p>
 * Для каждой платформы создайте свой класс конкретной реализации. Все они должны следовать общему интерфейсу,
 * который мы выделили перед этим.
 * <p>
 * Добавьте в класс абстракции ссылку на объект реализации. Реализуйте методы абстракции, делегируя основную работу
 * связанному объекту реализации.
 * <p>
 * Если у вас есть несколько вариаций абстракции, создайте для каждой из них свой подкласс.
 * <p>
 * Клиент должен подать объект реализации в конструктор абстракции, чтобы связать их воедино. После этого он может
 * свободно использовать объект абстракции, забыв о реализации.
 */
public class Bridge {
}

interface DbDriver {
    void addData(String data);

    String getData();

    boolean clear();
}

class MongoDriver implements DbDriver {

    private String mongoData;

    @Override
    public void addData(String data) {
        System.out.println(String.format("Setting data %s to Mongo DB.", data));
        mongoData = data;
    }

    @Override
    public String getData() {
        System.out.println(String.format("Extracting data %s from Mongo DB.", mongoData));
        return mongoData;
    }

    @Override
    public boolean clear() {
        System.out.println("Purge data in Mongo DB.");
        return true;
    }
}

class OracleDriver implements DbDriver {

    private String oracleData;

    @Override
    public void addData(String data) {
        System.out.println(String.format("Setting data %s to Oracle DB.", data));
        oracleData = data;
    }

    @Override
    public String getData() {
        System.out.println(String.format("Extracting data %s from Oracle DB.", oracleData));
        return oracleData;
    }

    @Override
    public boolean clear() {
        System.out.println("Purge data in Oracle DB.");
        return true;
    }
}

class DbController {
    protected DbDriver dbDriver;

    void addData(String data) {
        dbDriver.addData(data);
    }

    String getData() {
        return dbDriver.getData();
    }

    boolean clear() {
        return dbDriver.clear();
    }
}

class AdvancedDbController extends DbController {

    String setNewAndGerFormer(String newData) {
        String oldData = dbDriver.getData();
        dbDriver.addData(newData);
        return oldData;
    }

    String clearAndGetData() {
        String data = dbDriver.getData();
        dbDriver.clear();
        return data;
    }
}
