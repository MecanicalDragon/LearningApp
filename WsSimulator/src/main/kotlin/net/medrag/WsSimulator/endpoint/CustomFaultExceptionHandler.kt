package net.medrag.WsSimulator.endpoint

import net.medrag.WsSimulator.model.CustomFaultException
import net.medrag.schematypes.Fehler
import org.springframework.stereotype.Component
import org.springframework.ws.context.MessageContext
import org.springframework.ws.server.EndpointExceptionResolver
import org.springframework.ws.soap.SoapMessage
import java.lang.Exception
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.namespace.QName


/**
 * {@author} Stanislav Tretyakov
 * 08.11.2019
 */
@Component
class CustomFaultExceptionHandler : EndpointExceptionResolver {

    companion object {
        private const val NAMESPACE_URI = "http://schemaTypes.medrag.net"
        private const val FEHLER = "devFehler"
    }

    override fun resolveException(messageContext: MessageContext, endpoint: Any, exception: Exception): Boolean {
        if (exception is CustomFaultException) {

//            val soapResponse = messageContext.response as SoapMessage
//            val soapBody = soapResponse.soapBody
//            val payload = soapBody.payloadResult
//            val fault = soapBody.addClientOrSenderFault("Application Error", Locale.ENGLISH)

//            val detail = fault.addFaultDetail()
//            var result = detail.result

            val fault = (messageContext.response as SoapMessage).soapBody.addClientOrSenderFault("Application Error", Locale.ENGLISH)
            fault.faultActorOrRole = "WS-SIM"
            val result = fault.addFaultDetail().result

            val fehler = Fehler()
            fehler.timestamp = LocalDateTime.now()
            fehler.reason = exception.msg
            fehler.comment = "Input data more intently, you, stupid!"

            val jaxbElement = JAXBElement<Fehler>(QName(NAMESPACE_URI, FEHLER), Fehler::class.java, fehler)
            val jaxbContext = JAXBContext.newInstance(Fehler::class.java)
            val marshaller = jaxbContext.createMarshaller()
            marshaller.marshal(jaxbElement, result)
        }
        return true
    }
}