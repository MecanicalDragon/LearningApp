package patterns.structure;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to evolve object functionality, wrapping it in different wrappers
 * Use it, when:
 * > you need to add functionality 'on flight', silently from execution code.
 * > you can't extend target object
 * <p>
 * + Flexibility higher than extensions'
 * + 'on flight'
 * + segregated commitments
 * <p>
 * - difficult to configure object, wrapped several times
 * - a lot of tiny classes
 * <p>
 * IMPLEMENTATION
 * <p>
 * Убедитесь, что в вашей задаче есть один основной компонент и несколько опциональных дополнений или надстроек над ним.
 * <p>
 * Создайте интерфейс компонента, который описывал бы общие методы как для основного компонента, так и для его дополнений.
 * <p>
 * Создайте класс конкретного компонента и поместите в него основную бизнес-логику.
 * <p>
 * Создайте базовый класс декораторов. Он должен иметь поле для хранения ссылки на вложенный объект-компонент.
 * Все методы базового декоратора должны делегировать действие вложенному объекту.
 * <p>
 * И конкретный компонент, и базовый декоратор должны следовать одному и тому же интерфейсу компонента.
 * <p>
 * Теперь создайте классы конкретных декораторов, наследуя их от базового декоратора. Конкретный декоратор должен
 * выполнять свою добавочную функцию, а затем (или перед этим) вызывать эту же операцию обёрнутого объекта.
 * <p>
 * Клиент берёт на себя ответственность за конфигурацию и порядок обёртывания объектов.
 */
public class DecoratorV2 {
    public static void main(String[] args) {
        String data = "some data";
        FileData fileData = new FileData();
        DataSource source = new DataCompressor(new DataEncryptor(new FileDataSource(fileData)));
        source.writeData(data);
        fileData.printData();
        System.out.println(source.readData());
    }
}

interface DataSource {
    void writeData(String data);

    String readData();
}

class FileData {
    String data;

    void printData() {
        System.out.println("Actual data is: " + data);
    }
}

class FileDataSource implements DataSource {

    FileData data;

    FileDataSource(FileData data) {
        this.data = data;
    }

    @Override
    public void writeData(String data) {
        this.data.data = data;
    }

    @Override
    public String readData() {
        return this.data.data;
    }
}

class DataEncryptor implements DataSource {

    private DataSource wrappee;
    private String encryption = "encrypted:";

    public DataEncryptor(DataSource wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void writeData(String data) {
        data = encryption.concat(data);
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        String data = wrappee.readData();
        data = data.substring(10);
        return data;
    }
}

class DataCompressor implements DataSource {
    private DataSource wrappee;
    private String compressor = "compressed:";

    public DataCompressor(DataSource wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void writeData(String data) {
        data = compressor.concat(data);
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        String data = wrappee.readData();
        data = data.substring(11);
        return data;
    }
}
