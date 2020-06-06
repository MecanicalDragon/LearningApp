package net.medrag.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
@Data
@Entity
@Table(name = "customer")
@EqualsAndHashCode(callSuper = true)
public class Customer extends EntityHeader {
    private String name;
    private String city;
    private String country;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer", fetch = FetchType.LAZY)
}
