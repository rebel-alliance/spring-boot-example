package org.alliance.rebel.tomcat;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.alliance.rebel.tomcat.util.GitProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.alliance")
public class SampleTomcatApplication {

  private Log logger = LogFactory.getLog(getClass());

  public static void main(String[] args) throws Exception {
    SpringApplication.run(SampleTomcatApplication.class, args);
  }

  @Autowired
  private GitProperties gitProperties;

  @Bean
  public String buildTime() {
    return gitProperties.getBuildTime();
  }

  @Bean
  protected ServletContextListener listener() {
    return new ServletContextListener() {
      @Override
      public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ServletContext destroyed");
      }

      @Override
      public void contextInitialized(ServletContextEvent sce) {
        logger.info("ServletContext initialized");
      }
    };
  }

  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
    tomcat.setTomcatConnectorCustomizers(Arrays.asList(new ConnectorCustomizer()));
    tomcat.setPersistSession(false);
    tomcat.setRegisterDefaultServlet(false);
    return tomcat;
  }

//  @Bean
//  public FilterRegistrationBean filterRegistrationBean() {
//    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//    registrationBean.setFilter(new ExceptionForwardFilter());
//    registrationBean.setOrder(1);
//    return registrationBean;
//  }

  public class ExceptionForwardFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      logger.debug("filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
      try {
        chain.doFilter(request, response);
      } catch (Exception e) {
        request.getRequestDispatcher("/error").forward(request, response);
      }
    }

    @Override
    public void destroy() {
    }
  }

}
