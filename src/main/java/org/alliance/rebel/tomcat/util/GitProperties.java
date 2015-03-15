package org.alliance.rebel.tomcat.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class GitProperties {

  private static GitProperties instance;

  public static GitProperties getInstance() {
    return instance;
  }

  private String branch;

  private String buildTime;

  private String commitId;

  private String label;

  public String getBranch() {
    return branch;
  }

  public String getBuildTime() {
    return buildTime;
  }

  public String getCommitId() {
    return commitId;
  }

  public String getLabel() {
    return label;
  }

  @PostConstruct
  public void init() {
    instance = this;
    URL resource = getClass().getResource("/git.properties");
    if (resource != null) {
      try {
        Properties props = new Properties();
        props.load(resource.openStream());
        this.branch = props.getProperty("git.branch");
        this.commitId = props.getProperty("git.commit.id");
        this.buildTime = props.getProperty("git.build.time");
        this.label = props.getProperty("git.gitLabel");
      } catch (IOException e) {
        instance = this;
      }
    }
  }

}
