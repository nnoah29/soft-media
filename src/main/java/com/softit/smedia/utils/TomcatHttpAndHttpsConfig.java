package com.softit.smedia.utils;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatHttpAndHttpsConfig {

  /*  @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }
*/
    /*private Connector httpConnector() {
        Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(90211);
        connector.setSecure(false);
        return connector;
    }*/
}
