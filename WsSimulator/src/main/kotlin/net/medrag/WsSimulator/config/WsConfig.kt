package net.medrag.WsSimulator.config

import net.medrag.WsSimulator.endpoint.CustomFaultExceptionInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.config.annotation.WsConfigurerAdapter
import org.springframework.ws.server.EndpointInterceptor
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.xml.xsd.XsdSchema


/**
 * {@author} Stanislav Tretyakov
 * 31.10.2019
 */
@EnableWs
@Configuration
class WsConfig : WsConfigurerAdapter() {

    @Value("\${ws.sim.validateRequest}")
    lateinit var validateRequest: String
    @Value("\${ws.sim.validateResponse}")
    lateinit var validateResponse: String
    @Value("\${ws.sim.validating.schema}")
    lateinit var validationSchema: String

    @Bean
    fun messageDispatcherServlet(applicationContext: ApplicationContext): ServletRegistrationBean<*> {
        val servlet = MessageDispatcherServlet()
        servlet.setApplicationContext(applicationContext)
        servlet.isTransformWsdlLocations = true
        return ServletRegistrationBean(servlet, "/ws/*")
    }

    @Bean(name = ["wsdl"])  // WSDL-file: <path-to-app>/<servlet-url-mapping>/<this-bean-name>.wsdl
    fun defaultWsdl11Definition(schema: XsdSchema): DefaultWsdl11Definition {
        val wsdl11Definition = DefaultWsdl11Definition()
        wsdl11Definition.setPortTypeName("WS")
        wsdl11Definition.setLocationUri("/ws")
        wsdl11Definition.setTargetNamespace("http://schemaTypes.medrag.net")
        wsdl11Definition.setSchema(schema)
        return wsdl11Definition
    }

    @Bean
    fun schema(): XsdSchema {
        return SimpleXsdSchema(ClassPathResource(validationSchema))
    }

    override fun addInterceptors(interceptors: MutableList<EndpointInterceptor>) {
        interceptors.add(CustomFaultExceptionInterceptor(validateRequest, validateResponse, validationSchema))
    }
}
