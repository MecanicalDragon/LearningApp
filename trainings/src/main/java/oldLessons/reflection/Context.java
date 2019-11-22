package oldLessons.reflection;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.activity.InvalidActivityException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    public static final String TAG_BEAN = "bean";
    public static final String TAG_PROPERTY = "properties";
    List<Bean> beans = new ArrayList<>();
    private Map<String, Object>objectById = new HashMap<>();

    public Context(String xmlPath) throws SAXException, ParserConfigurationException, ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException, InvalidConfigurationException,
            IOException {
        // parse xml
        parseXml(xmlPath);
        instantiateBeans();
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidConfigurationException, IOException {
        Context context = new Context(Context.class.getResource("/old_lessons/reflection.xml").getPath());
        Car car = (Car)context.getBean("car");
//        Engine engine = (Engine)context.getBean("engine");
//        GearBox gearBox = (Manual)context.getBean("manual");
        System.out.println(car);

    }

    private Object getBean(String beanName) {
        return objectById.get(beanName);
    }

    private void instantiateBeans() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            NoSuchFieldException, InvalidConfigurationException {
        for (Bean bean : beans){
//            System.out.println("working with "+ bean);
            Class<?> aClass = Class.forName(bean.getClassName());
            Object o = aClass.newInstance();
            processAnnotation(aClass, o);
            for (String id : bean.getProperties().keySet()) {
                Field field = getField(aClass, id);
                if (field == null){
                    throw new InvalidConfigurationException("Failed to set field " + id + " for class " + aClass.getName());
                }
                field.setAccessible(true);
                Property property = bean.getProperties().get(id);
                switch (property.getType()){
                    case VALUE:
                        field.set(o, convert(field.getType().getName(), property.getValue()));
                        break;
                    case REF:
                        String refName = property.getValue();
                        if (objectById.containsKey(refName)){
                            field.set(o, objectById.get(refName));
                        } else {
                            throw new InvalidConfigurationException(" Failed to instantiate bean by ref: " + id);
                        }
                        break;
                    default: throw new InvalidConfigurationException("Failed to set up property");
                }
            }
            objectById.put(bean.getId(), o);
        }

    }

    private void processAnnotation(Class<?> aClass, Object o) throws InvalidConfigurationException, IllegalAccessException {
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(AutoInject.class)){
                AutoInject ai = field.getAnnotation(AutoInject.class);
                if (ai.isRequired() && !objectById.containsKey(field.getType().getName().toLowerCase())) {
                    throw new InvalidConfigurationException("Failed parse @Auto " + field.getName() + " " + field.getType());
                } else {
                    if (objectById.containsKey(field.getType().getName().toLowerCase())) {
                        Object ob = objectById.get(field.getType().getName().toLowerCase());
                        field.setAccessible(true);
                        field.set(o, ob);
                    }
                }
            }
        }
    }

    private Object convert(String typeName, String value) throws InvalidConfigurationException {
        switch (typeName){
            case "int":
            case "Integer":
                return Integer.valueOf(value);
            case "double":
            case "Double":
                return Double.valueOf(value);
            default: throw new InvalidConfigurationException("Invalid type: " + typeName);
        }
    }

    private Field getField(Class<?> aClass, String fieldName) throws NoSuchFieldException {
        try {
            return aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = aClass.getSuperclass();
            if (superclass == null) {
                throw e;
            } else {
                return getField(superclass, fieldName);
            }
        }
    }

    private void parseXml(String xmlPath) throws ParserConfigurationException, IOException, SAXException {
        Document document;
        // Document <- DocumentBuilder <- DocumentBuilderFactory
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlPath));
        Element root = document.getDocumentElement(); // get root
        NodeList nodes = root.getChildNodes(); // get beans list
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (TAG_BEAN.equals(node.getNodeName())){
                parseBean(node);
            }
        }

    }

    private void parseBean(Node bean) throws IOException {
        NamedNodeMap attributes = bean.getAttributes();
        Node id = attributes.getNamedItem("id");
        String idNodeValue = id.getNodeValue();

        String classNodeValue= attributes.getNamedItem("class").getNodeValue();
//        System.out.printf("id = %s, class = %s\n",idNodeValue, classNodeValue );

        Map<String, Property>properties = new HashMap<>();
        NodeList nodes = bean.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (TAG_PROPERTY.equals(node.getNodeName())){
                Property property = parseProperty(node);
                properties.put(property.getName(),  property);
//                System.out.printf("Property: %s", property);
            }
        }
        beans.add(new Bean(idNodeValue, classNodeValue, properties));
    }

    private Property parseProperty(Node node) throws IOException {
        NamedNodeMap attributes = node.getAttributes();
        String name = attributes.getNamedItem("name").getNodeValue();
        Node value = attributes.getNamedItem("value");
        if (value != null){
            return new Property(name, value.getNodeValue(), ValueType.VALUE);
        } else { Node ref = attributes.getNamedItem("ref");
            if (ref != null) {
                return new Property(name, ref.getNodeValue(), ValueType.REF);
            } else {
                throw new InvalidActivityException("Failed to find attribute ref or val: " + name);
            }
        }
    }
}
