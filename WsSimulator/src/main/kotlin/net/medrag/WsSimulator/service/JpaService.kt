package net.medrag.WsSimulator.service

import net.medrag.WsSimulator.model.CustomFaultException
import net.medrag.WsSimulator.model.Response
import org.springframework.stereotype.Service
import java.io.StringReader
import java.util.stream.Collectors
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory


/**
 * {@author} Stanislav Tretyakov
 * 05.11.2019
 */
@Service
class JpaService(private val repo: ResponseRepo) {

    fun addResponse(resp: Response, validate: Boolean): String {
        if (resp.req.isNotBlank() && resp.xml.isNotBlank()) {
            println("New data has been received.")
            try {
                if (validate) {
                    val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    val schema = factory.newSchema(JpaService::class.java.getResource("/xsd/root.xsd"))
                    val validator = schema.newValidator()
                    validator.validate(StreamSource(StringReader(resp.xml)))
                    println("XML String has been successfully validated against XSD Schema.")
                } else println("XML validation is turned off!")
                return repo.save(resp).req
            } catch (e: Exception) {
                println(e.message)
                throw RuntimeException(e.message)
            }
        } else {
            println("New data has been received, but at least one of it's fields is blank. No actions will be made.")
            throw RuntimeException("Fields are blank!")
        }
    }

    fun getById(id: String): String {
        val findById = repo.findById(id)
        return if (findById.isPresent) findById.get().xml else throw CustomFaultException("No response for request '$id' in the database!")
    }

    fun getAll() = repo.findAll().toList().stream().map { r -> r.req }.collect(Collectors.toList())

    fun getReqs() = repo.getReqs()

    fun remove(id: String): String {
        repo.deleteById(id)
        return id
    }
}