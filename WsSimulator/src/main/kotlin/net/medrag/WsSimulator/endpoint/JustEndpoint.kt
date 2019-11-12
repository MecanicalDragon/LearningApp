package net.medrag.WsSimulator.endpoint

import net.medrag.WsSimulator.service.JpaService
import lombok.extern.slf4j.Slf4j
import net.medrag.schematypes.DeveloperRequestType as Request
import net.medrag.schematypes.DeveloperResponseType as Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import javax.xml.bind.JAXBElement
import javax.xml.namespace.QName
import java.io.StringReader
import javax.xml.bind.JAXBContext
import javax.xml.stream.XMLInputFactory


/**
 * {@author} Stanislav Tretyakov
 * 31.10.2019
 */
@Slf4j
@Endpoint
class JustEndpoint(@Autowired private val jpa: JpaService) {

    companion object {
        private const val NAMESPACE_URI = "http://schemaTypes.medrag.net"
        private const val REQUEST = "DeveloperRequest"
        private const val RESPONSE = "DeveloperResponse"
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = REQUEST)
    @ResponsePayload
    fun getDeveloperXml(@RequestPayload request: Request): JAXBElement<Response> {
        println("Developer SOAP request has been performed.")
        val byId = jpa.getById(request.identifier)
        val jaxbContext = JAXBContext.newInstance(Response::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        val reader = StringReader(byId)
        val xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(reader)
        val response = unmarshaller.unmarshal(xmlReader, Response::class.java)
        return JAXBElement<Response>(QName(NAMESPACE_URI, RESPONSE), Response::class.java, response.value)
    }
}