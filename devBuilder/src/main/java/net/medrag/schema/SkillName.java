//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.08 at 09:41:22 PM MSK 
//


package net.medrag.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SkillName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SkillName"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Java"/&gt;
 *     &lt;enumeration value="Angular"/&gt;
 *     &lt;enumeration value="React"/&gt;
 *     &lt;enumeration value="Kotlin"/&gt;
 *     &lt;enumeration value="Sharp"/&gt;
 *     &lt;enumeration value="Rust"/&gt;
 *     &lt;enumeration value="C++"/&gt;
 *     &lt;enumeration value="Hibernate"/&gt;
 *     &lt;enumeration value="Spring"/&gt;
 *     &lt;enumeration value="SQL"/&gt;
 *     &lt;enumeration value="RabbitMQ"/&gt;
 *     &lt;enumeration value="ApacheMQ"/&gt;
 *     &lt;enumeration value="Kafka"/&gt;
 *     &lt;enumeration value="Docker"/&gt;
 *     &lt;enumeration value="Kubernetes"/&gt;
 *     &lt;enumeration value="OracleDB"/&gt;
 *     &lt;enumeration value="MySQL"/&gt;
 *     &lt;enumeration value="H2"/&gt;
 *     &lt;enumeration value="Tomcat"/&gt;
 *     &lt;enumeration value="Wildfly"/&gt;
 *     &lt;enumeration value="EJB"/&gt;
 *     &lt;enumeration value="HTML"/&gt;
 *     &lt;enumeration value="CSS"/&gt;
 *     &lt;enumeration value="Unity"/&gt;
 *     &lt;enumeration value="JavaScript"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SkillName")
@XmlEnum
public enum SkillName {

    @XmlEnumValue("Java")
    JAVA("Java"),
    @XmlEnumValue("Angular")
    ANGULAR("Angular"),
    @XmlEnumValue("React")
    REACT("React"),
    @XmlEnumValue("Kotlin")
    KOTLIN("Kotlin"),
    @XmlEnumValue("Sharp")
    SHARP("Sharp"),
    @XmlEnumValue("Rust")
    RUST("Rust"),
    @XmlEnumValue("C++")
    C("C++"),
    @XmlEnumValue("Hibernate")
    HIBERNATE("Hibernate"),
    @XmlEnumValue("Spring")
    SPRING("Spring"),
    SQL("SQL"),
    @XmlEnumValue("RabbitMQ")
    RABBIT_MQ("RabbitMQ"),
    @XmlEnumValue("ApacheMQ")
    APACHE_MQ("ApacheMQ"),
    @XmlEnumValue("Kafka")
    KAFKA("Kafka"),
    @XmlEnumValue("Docker")
    DOCKER("Docker"),
    @XmlEnumValue("Kubernetes")
    KUBERNETES("Kubernetes"),
    @XmlEnumValue("OracleDB")
    ORACLE_DB("OracleDB"),
    @XmlEnumValue("MySQL")
    MY_SQL("MySQL"),
    @XmlEnumValue("H2")
    H_2("H2"),
    @XmlEnumValue("Tomcat")
    TOMCAT("Tomcat"),
    @XmlEnumValue("Wildfly")
    WILDFLY("Wildfly"),
    EJB("EJB"),
    HTML("HTML"),
    CSS("CSS"),
    @XmlEnumValue("Unity")
    UNITY("Unity"),
    @XmlEnumValue("JavaScript")
    JAVA_SCRIPT("JavaScript");
    private final String value;

    SkillName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SkillName fromValue(String v) {
        for (SkillName c: SkillName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
