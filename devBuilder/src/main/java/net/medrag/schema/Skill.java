//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.08 at 09:41:22 PM MSK 
//


package net.medrag.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Skill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Skill"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Name" type="{http://schema.medrag.net}SkillName"/&gt;
 *         &lt;element name="Level" type="{http://schema.medrag.net}SkillLevel"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Skill", propOrder = {
    "name",
    "level"
})
public class Skill {

    @XmlElement(name = "Name", required = true)
    @XmlSchemaType(name = "string")
    protected SkillName name;
    @XmlElement(name = "Level", required = true)
    @XmlSchemaType(name = "string")
    protected SkillLevel level;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link SkillName }
     *     
     */
    public SkillName getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkillName }
     *     
     */
    public void setName(SkillName value) {
        this.name = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link SkillLevel }
     *     
     */
    public SkillLevel getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkillLevel }
     *     
     */
    public void setLevel(SkillLevel value) {
        this.level = value;
    }

}
