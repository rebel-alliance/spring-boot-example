/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.alliance.rebel.tomcat;

import java.util.Arrays;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="org.alliance")
//@EnableRedisHttpSession
public class SampleTomcatApplication {

  private Log logger = LogFactory.getLog(getClass());

  public static void main(String[] args) throws Exception {
    SpringApplication.run(SampleTomcatApplication.class, args);
  }

  @Autowired
  private GitProperties gitProperties;

  @Bean
  public String buildTime() {
    // System.out.println("BUILD TIME: " + gitProperties.getBuildTime());
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
    return tomcat;
  }

//  @Bean
//  public JedisConnectionFactory connectionFactory() {
//    JedisConnectionFactory factory = new JedisConnectionFactory();
//    factory.setHostName("redis");
//    return factory;
//  }

}
