package com.mycompany.soapwebservice.spring.controller;

import com.mycompany.soapwebservice.util.CommonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableWs
@Configuration
@ComponentScan
public class WebServiceConfiguration extends WsConfigurerAdapter {

    private static final Logger logger = LogManager.getLogger(WebServiceConfiguration.class);

    @Override
    // The below Interceptor is to enable payload XML Schema validation.
    public void addInterceptors(List<EndpointInterceptor> endpointInterceptors) {
        PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
        payloadValidatingInterceptor.setValidateRequest(true);
        payloadValidatingInterceptor.setValidateResponse(true);
        XsdSchemaCollection xsdSchemaCollection = new XsdSchemaCollection() {
            @Override
            public XsdSchema[] getXsdSchemas() {
                return null;
            }

            @Override
            public XmlValidator createValidator() {
                try {
                    return XmlValidatorFactory.createValidator(getXMLSchemaResources(), XmlValidatorFactory.SCHEMA_W3C_XML);
                } catch (IOException ioException) {
                    logger.error("Unexpected exception occurred while creating XMLValidator. Program should be stopped. Exception details:"
                            + CommonMethods.convertExceptionIntoString.apply(ioException));
                }
                return null;
            }
        };
        payloadValidatingInterceptor.setXsdSchemaCollection(xsdSchemaCollection);
        endpointInterceptors.add(payloadValidatingInterceptor);
    }

    @Bean
    public Resource[] getXMLSchemaResources() {
        List<Resource> xmlSchemaResourcesList = new ArrayList<>();
        xmlSchemaResourcesList.add(new ClassPathResource("./wsdl_and_xsd/Address.xsd"));
        xmlSchemaResourcesList.add(new ClassPathResource("./wsdl_and_xsd/EmployeeDetails.xsd"));
        return xmlSchemaResourcesList.toArray(new Resource[xmlSchemaResourcesList.size()]);
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(applicationContext);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        messageDispatcherServlet.setTransformSchemaLocations(true);
        return new ServletRegistrationBean<MessageDispatcherServlet>(messageDispatcherServlet, "/service/*");
    }

    @Bean(name = "EmployeeDetailsService")
    public Wsdl11Definition defaultWsdl11Definition() {
        SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
        simpleWsdl11Definition.setWsdl(new ClassPathResource("./wsdl_and_xsd/EmployeeDetails_SOAP_Webservices.wsdl"));
        return simpleWsdl11Definition;
    }
}
