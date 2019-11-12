package net.medrag.WsSimulator.service

import net.medrag.WsSimulator.model.Response
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


/**
 * {@author} Stanislav Tretyakov
 * 05.11.2019
 */
@Repository
interface ResponseRepo : CrudRepository<Response, String>{

@Query("SELECT r.req FROM Response r")
fun getReqs():List<String>
}
