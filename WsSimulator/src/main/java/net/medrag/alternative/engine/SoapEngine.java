package net.medrag.alternative.engine;

import net.medrag.alternative.validation.CustomFault;
import net.medrag.alternative.validation.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.client.support.interceptor.PayloadValidatingInterceptor;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.io.IOException;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
//@Component
public class SoapEngine {

    @Value("${app.endpoint.url}")
    private String endpoint;

    @Value("${app.validationSchema}")
    private String schema;

    @Value("${app.validateRequest}")
    private String validateRequest;

    @Value("${app.validateResponse}")
    private String validateResponse;

    @Autowired
    private WebServiceTemplate templateInstance;

    // root request/response objects should be here
    static final Class[] roots = new Class[] { CustomFault.class, MessageContext.class};

    @Bean
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(roots);
        Resource schemaResource = new ClassPathResource(schema);
        jaxb2Marshaller.setSchema(schemaResource);
        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller());
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
        webServiceTemplate.setDefaultUri(endpoint);
        webServiceTemplate.setCheckConnectionForFault(true);
        ClientInterceptor[] interceptors = new ClientInterceptor[] { validatingInterceptor() };
        webServiceTemplate.setInterceptors(interceptors);
        return webServiceTemplate;
    }

    @Bean
    public XsdSchema schema() {
        return new SimpleXsdSchema(new ClassPathResource(schema));
    }

    @Bean
    public PayloadValidatingInterceptor validatingInterceptor() {
        PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        interceptor.setValidateRequest(Boolean.parseBoolean(validateRequest));
        interceptor.setValidateResponse(Boolean.parseBoolean(validateResponse));
        try {
            interceptor.setXsdSchema(schema());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return interceptor;
    }
}

