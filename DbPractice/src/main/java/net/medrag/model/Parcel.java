package net.medrag.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
@Data
@Entity
@Table(name = "parcel")
@EqualsAndHashCode(callSuper = true)
public class Parcel extends EntityHeader {
    private String name;
    private String departure;
    private String destination;
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private Customer owner;
}
