package org.alliance.rebel.tomcat;

import org.alliance.rebel.tomcat.util.GitProperties;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.alliance")
public class SampleTomcatApplication {

  public static void main(String[] args) throws Exception {
    LogFactory.getLog(SampleTomcatApplication.class).debug("main");
    SpringApplication.run(SampleTomcatApplication.class, args);
  }

  @Autowired
  private GitProperties gitProperties;

  @Bean
  public String buildTime() {
    return gitProperties.getBuildTime();
  }

}
