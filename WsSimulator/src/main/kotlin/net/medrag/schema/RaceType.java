//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.12 at 12:52:28 PM MSK 
//


package net.medrag.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RaceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RaceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Jew"/&gt;
 *     &lt;enumeration value="Hohol"/&gt;
 *     &lt;enumeration value="Vatnik"/&gt;
 *     &lt;enumeration value="Pindos"/&gt;
 *     &lt;enumeration value="Nigger"/&gt;
 *     &lt;enumeration value="Jap"/&gt;
 *     &lt;enumeration value="Fritz"/&gt;
 *     &lt;enumeration value="Makaronnik"/&gt;
 *     &lt;enumeration value="Lyagushatnik"/&gt;
 *     &lt;enumeration value="Churka"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RaceType")
@XmlEnum
public enum RaceType {

    @XmlEnumValue("Jew")
    JEW("Jew"),
    @XmlEnumValue("Hohol")
    HOHOL("Hohol"),
    @XmlEnumValue("Vatnik")
    VATNIK("Vatnik"),
    @XmlEnumValue("Pindos")
    PINDOS("Pindos"),
    @XmlEnumValue("Nigger")
    NIGGER("Nigger"),
    @XmlEnumValue("Jap")
    JAP("Jap"),
    @XmlEnumValue("Fritz")
    FRITZ("Fritz"),
    @XmlEnumValue("Makaronnik")
    MAKARONNIK("Makaronnik"),
    @XmlEnumValue("Lyagushatnik")
    LYAGUSHATNIK("Lyagushatnik"),
    @XmlEnumValue("Churka")
    CHURKA("Churka");
    private final String value;

    RaceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RaceType fromValue(String v) {
        for (RaceType c: RaceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
