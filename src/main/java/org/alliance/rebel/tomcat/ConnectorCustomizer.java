package org.alliance.rebel.tomcat;

import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

public class ConnectorCustomizer implements TomcatConnectorCustomizer {
  private static final long NINTEY = TimeUnit.SECONDS.toMillis(90);

  @Override
  public void customize(Connector connector) {
    connector.setAttribute("connectionTimeout", NINTEY);
  }

}
