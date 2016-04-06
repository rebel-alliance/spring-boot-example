package org.alliance.rebel.tomcat;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.stereotype.Component;

@Component
public class SampleFilterRegistrationBean extends FilterRegistrationBean {

  private Log logger = LogFactory.getLog(getClass());

  @PostConstruct
  public void init() {
    setFilter(new TestFilter());
    setOrder(1);
  }

  public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      logger.debug("filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
      logger.debug("filtering " + ((HttpServletRequest) request).getRequestURI());
      chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
      logger.debug("filter destroy");
    }
  }
}
