package net.medrag.WsSimulator.model
import javax.persistence.*


/**
 * {@author} Stanislav Tretyakov
 * 05.11.2019
 */
@Entity
@Table(name = "RESPONSE")
data class Response(
        @Id
        @Column(nullable = false, name = "req", unique = true) var req: String,

        @Lob
        @Column(nullable = false, name = "xml") var xml: String) {

    constructor() : this("", "")
}