package net.medrag.WsSimulator.endpoint


/**
 * {@author} Stanislav Tretyakov
 * 12.11.2019
 */

import net.medrag.WsSimulator.model.CustomFaultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.ws.context.MessageContext
import org.springframework.ws.server.EndpointInterceptor
import org.xml.sax.SAXException

import javax.xml.XMLConstants
import javax.xml.transform.dom.DOMSource
import javax.xml.validation.SchemaFactory
import java.io.IOException

/**
 * {@author} Stanislav Tretyakov
 * 19.06.2019
 */
@Component
class CustomFaultExceptionInterceptor @Autowired
constructor(@param:Value("\${ws.sim.validateRequest}") private val validateRequest: String,
            @param:Value("\${ws.sim.validateResponse}") private val validateResponse: String,
            @param:Value("\${ws.sim.validating.schema}") private val validationSchema: String) : EndpointInterceptor {

    @Throws(Exception::class)
    override fun handleRequest(messageContext: MessageContext, endpoint: Any): Boolean {

        if (java.lang.Boolean.parseBoolean(validateRequest)) {

            val source = messageContext.request.payloadSource
            val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
            val schema = factory.newSchema(CustomFaultExceptionInterceptor::class.java.getResource(validationSchema))

            try {
                val validator = schema.newValidator()
                validator.validate(source)
            } catch (e: SAXException) {
                throw CustomFaultException(e.message.toString())
            } catch (e: IOException) {
                throw CustomFaultException(e.message.toString())
            }

        }
        return true
    }

    @Throws(Exception::class)
    override fun handleResponse(messageContext: MessageContext, endpoint: Any): Boolean {

        if (java.lang.Boolean.parseBoolean(validateResponse)) {

            val source = messageContext.response.payloadSource
            if ((source as DOMSource).node.localName != "DeveloperResponse") {
                val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                val schema = factory.newSchema(CustomFaultExceptionInterceptor::class.java.getResource(validationSchema))

                try {
                    val validator = schema.newValidator()
                    validator.validate(source)
                } catch (e: SAXException) {
                    throw CustomFaultException(e.message.toString())
                } catch (e: IOException) {
                    throw CustomFaultException(e.message.toString())
                }

            }
        }
        return true
    }

    @Throws(Exception::class)
    override fun handleFault(messageContext: MessageContext, endpoint: Any): Boolean {
        return false
    }

    @Throws(Exception::class)
    override fun afterCompletion(messageContext: MessageContext, endpoint: Any, ex: Exception?) {

    }

}
