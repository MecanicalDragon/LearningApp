package net.medrag.alternative.validation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
@XmlRootElement(name = "CustomFault", namespace = "namespace")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomFault")
public class CustomFault {
}
