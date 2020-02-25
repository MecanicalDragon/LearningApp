package net.medrag.alternative.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
@Component
public class CustomValidationInterceptor implements EndpointInterceptor {

    @Autowired
    public CustomValidationInterceptor(@Value("${app.validateRequest}") String validateRequest,
                                       @Value("${app.validateResponse}") String validateResponse,
                                       @Value("${app.request.validating.schema}") String validationSchema) {
        this.validateRequest = validateRequest;
        this.validateResponse = validateResponse;
        this.validationSchema = validationSchema;
    }

    private String validateRequest;
    private String validateResponse;
    private String validationSchema;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {

        if (Boolean.parseBoolean(validateRequest)) {

            Source source = messageContext.getRequest().getPayloadSource();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(CustomValidationInterceptor.class.getResource(validationSchema));

            try {
                Validator validator = schema.newValidator();
                validator.validate(source);
            } catch (SAXException | IOException e) {
                throw new RequestValidationException(e.getMessage());
            }
        }
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {

        if (Boolean.parseBoolean(validateResponse)) {

            Source source = messageContext.getResponse().getPayloadSource();
            if (!((DOMSource) source).getNode().getLocalName().equals("CustomFault")) {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(CustomValidationInterceptor.class.getResource(validationSchema));

                try {
                    Validator validator = schema.newValidator();
                    validator.validate(source);
                } catch (SAXException | IOException e) {
                    throw new ResponseValidationException(e.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {

    }

}
