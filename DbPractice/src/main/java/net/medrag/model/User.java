package net.medrag.model;

import lombok.Data;

import javax.persistence.*;

/**
 * {@author} Stanislav Tretyakov
 * 17.02.2020
 */
@Data
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //  OTHERWISE USE SEQUENCE (bug for hibernate and h2):
//    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
//    @GeneratedValue(generator = "user_generator")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;
}
