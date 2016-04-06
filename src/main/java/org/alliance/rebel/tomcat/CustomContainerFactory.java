package org.alliance.rebel.tomcat;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.catalina.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomContainerFactory extends TomcatEmbeddedServletContainerFactory implements InitializingBean {

  private Log logger = LogFactory.getLog(getClass());

  @Override
  protected void postProcessContext(Context context) {
    // TODO Auto-generated method stub
    super.postProcessContext(context);
    if (System.getenv("VCAP_APPLICATION") != null) {
      // force https when runnning on cfapps.io
      context.addConstraint(buildSecurityConstraint());
      logger.debug("security constraint added");
    } else {
      logger.debug("security constraint not added");
    }
  }

  protected SecurityConstraint buildSecurityConstraint() {
    SecurityConstraint securityConstraint = new SecurityConstraint();
    securityConstraint.setUserConstraint("CONFIDENTIAL");
    SecurityCollection collection = new SecurityCollection();
    collection.addPattern("/*");
    securityConstraint.addCollection(collection);
    return securityConstraint;
  }

  @PostConstruct
  public void afterPropertiesSet() throws Exception {
    setTomcatConnectorCustomizers(Arrays.asList(new ConnectorCustomizer()));
    setPersistSession(false);
    setRegisterDefaultServlet(false);
  }
}
