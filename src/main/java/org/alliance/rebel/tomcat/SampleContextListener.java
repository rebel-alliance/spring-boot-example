package org.alliance.rebel.tomcat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class SampleContextListener implements ServletContextListener {
  private Log logger = LogFactory.getLog(getClass());

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    logger.info("ServletContext destroyed");
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    logger.info("ServletContext initialized");
  }
}
