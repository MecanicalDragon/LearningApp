package net.medrag.alternative.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointExceptionResolver;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
@Component
public class CustomValidationExceptionHandler implements EndpointExceptionResolver {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomValidationExceptionHandler.class);

    @Override
    public boolean resolveException(MessageContext messageContext, Object endpoint, Exception ex) {
        if (ex instanceof RequestValidationException) {
            LOGGER.warn("Request validation has failed because of {}", ex.getMessage());
            LOGGER.warn("Request handling will not be proceeded.");

            SoapMessage request = (SoapMessage) messageContext.getRequest();
            Source payloadSource = request.getSoapBody().getPayloadSource();
            DOMSource bodyDomSource = (DOMSource) payloadSource;

//            bodyDomSource.getNode().getLocalName()
            try {
                JAXBContext context = JAXBContext.newInstance(net.medrag.alternative.validation.MessageContext.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                net.medrag.alternative.validation.MessageContext msg
                        = (net.medrag.alternative.validation.MessageContext) unmarshaller.unmarshal(bodyDomSource);
                LOGGER.error("SOAP message context retrieved: {}", msg);
            } catch (JAXBException e) {
                LOGGER.error("Marshalling exception during custom error message compiling: {}", e.getMessage());
            }

            CustomFault fault = new CustomFault();

            SoapMessage response = (SoapMessage) messageContext.getResponse();
            SoapBody soapBody = response.getSoapBody();
            Result payloadResult = soapBody.getPayloadResult();

            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(CustomFault.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(fault, payloadResult);
            } catch (JAXBException e) {
                LOGGER.error("Exception during marshalling validation response: {}", e.getMessage());
            }
            return true;
        } else if (ex instanceof ResponseValidationException) {
            LOGGER.warn("Invalid response has been sent. Inaccuracies start with: {}", ex.getMessage());
            return true;
        } else return false;
    }
}
