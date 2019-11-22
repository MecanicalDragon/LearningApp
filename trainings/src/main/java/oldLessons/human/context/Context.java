package oldLessons.human.context;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
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
    private final static String BODY_PART = "bodyPart";
    private final static String PROPERTY = "property";
    private String xmlPath;
    private String pathCorrection = "oldLessons.human.";
    private List<BodyPart>bodyParts = new ArrayList<>();
    public Map<String, Object>readyParts = new HashMap<>();

    public Context(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public void instantiateParts() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        for(BodyPart b : bodyParts){
            Class<?> aClass = Class.forName(pathCorrection + b.classs);
            Object bodyPart = aClass.newInstance();
            for (Property p : b.properties){
                Field declaredField = aClass.getDeclaredField(p.name);
                declaredField.setAccessible(true);
                String value = p.value;
                if (declaredField.getType().getName().equals("int")){
                    declaredField.set(bodyPart, Integer.valueOf(value));
                } else {
                    if (declaredField.getType().getName().equals("boolean")){
                        declaredField.set(bodyPart, Boolean.valueOf(value));
                    } else {
                        if (declaredField.getType().isEnum()){
                            declaredField.set(bodyPart, Enum.valueOf((Class<Enum>) declaredField.getType(), value));
                        } else {
                            if (declaredField.getType().getName().equals("java.lang.String")){
                                declaredField.set(bodyPart, value);
                            } else {
                                String name = value.substring(value.lastIndexOf(".") + 1).toLowerCase();
                                Object newPart = readyParts.get(name);
                                if (p.mods != null) {
                                        Class<?> tunedClass = newPart.getClass();
                                        Object tunedPart = tunedClass.newInstance();
                                    for (int i = 0; i < p.mods.length; i+=2) {
                                        Field tunedField = tunedClass.getDeclaredField(p.mods[i]);
                                        tunedField.setAccessible(true);
                                        String tunedValue = p.mods[i + 1];
                                        if (tunedField.getType().getName().equals("int")){
                                            tunedField.set(tunedPart, Integer.valueOf(tunedValue));
                                        } else{
                                            if (tunedField.getType().getName().equals("boolean")){
                                                tunedField.set(tunedPart, Boolean.valueOf(tunedValue));
                                            }
                                        }
                                    }
                                    declaredField.set(bodyPart, tunedPart);
                                } else {
                                    declaredField.set(bodyPart, newPart);
                                }
                            }
                        }
                    }
                }

            }
            readyParts.put(b.name, bodyPart);
        }
    }

    public void parseXML() {
        try {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlPath));
        Element human = doc.getDocumentElement();
            parseNode(human);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void parseNode(Node bodyPart) {
        //get list of child nodes
        NodeList childNodes = bodyPart.getChildNodes();
        List<Property> properties = new ArrayList<>();

        for (int i = 0; i < childNodes.getLength(); i++){
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals(BODY_PART)){
                //recursive call of getting list of child nodes
                parseNode(childNode);
            } else {
                if (childNode.getNodeName().equals(PROPERTY)){
                    // get attributes of property
                    NamedNodeMap attributes = childNode.getAttributes();
                    // get attribute name
                    Node nameNode = attributes.getNamedItem("name");
                    String name = nameNode.getNodeValue();
                    // get attribute value
                    Node valueNode = attributes.getNamedItem("value");
                    String value = valueNode.getNodeValue();
                    //add modifiers
                    Node modNode = attributes.getNamedItem("mods");
                    String[] mods = null;
                    if (modNode != null) {
                        mods = modNode.getNodeValue().split("=");
                    }
                    // add new property
                    properties.add(new Property(name, value, mods));
                }
            }

        }
        // "if" needs cause "\n" in xml are taken for nodes too - wtf?!
        if (bodyPart.getNodeName().equals(BODY_PART)){
        // get properties of node
        NamedNodeMap attributes = bodyPart.getAttributes();
        // get attribute name
        Node nameNode = attributes.getNamedItem("name");
        String name = nameNode.getNodeValue();
        // get class node
        Node classNode = attributes.getNamedItem("class");
        String classs = classNode.getNodeValue();
        bodyParts.add(new BodyPart(name, classs, properties));
        }

    }

    public Object[] autocreate(){
        List<Object>autocreated = new ArrayList<>();
        for (Object bodyPart : readyParts.values()) {
            if (bodyPart.getClass().isAnnotationPresent(AutoCreate.class)){
                AutoCreate ac = bodyPart.getClass().getAnnotation(AutoCreate.class);
                if (ac.value()){
                    autocreated.add(bodyPart);
                }
            }
        }
        return autocreated.toArray();
    }

}
