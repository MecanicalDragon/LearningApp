package net.medrag.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * {@author} Stanislav Tretyakov
 * 14.02.2020
 */
@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Column(name = "age")
    Integer age;

}
