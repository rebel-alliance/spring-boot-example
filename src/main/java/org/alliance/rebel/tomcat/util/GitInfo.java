package org.alliance.rebel.tomcat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public final class GitInfo {
  private static final String GIT_BRANCH = "git.branch";
  private static final String GIT_BUILD_TIME = "git.build.time";
  private static final String GIT_COMMIT_ID = "git.commit.id";
  private static final String GIT_COMMIT_TAG = "git.commitTag";
  private static final String GIT_DIR = ".git";
  private static final String GIT_GIT_HUB_URL = "git.gitHubUrl";
  private static final String GIT_GIT_LABEL = "git.gitLabel";

  private static void createOutputDirectory(File outputFile) throws IOException {
    outputFile.getParentFile().mkdirs();
    if (!outputFile.getParentFile().exists()) {
      throw new IOException("unable to create parent directory for file " + outputFile.getAbsolutePath());
    }
  }

  public static void main(String[] args) throws IOException {

    File gitDirectory = new File(args[0]);

    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setGitDir(gitDirectory).build();
    ObjectId head = repository.getRef("HEAD").getObjectId();

    File outputFile = new File(args[1]);
    createOutputDirectory(outputFile);

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    Properties props = new Properties();

    for (Entry<String, Ref> entry : repository.getTags().entrySet()) {
      if (head.equals(entry.getValue().getObjectId())) {
        props.put(GIT_COMMIT_TAG, entry.getKey());
        break;
      }
    }

    props.put(GIT_BRANCH, repository.getBranch());
    props.put(GIT_COMMIT_ID, head.getName());
    props.put(GIT_BUILD_TIME, format.format(new Date()));

    String remoteUrl = repository.getConfig().getString("remote", "origin", "url");
    remoteUrl = remoteUrl.replace("git@github.com:", "https://github.com/");

    if (props.containsKey(GIT_COMMIT_TAG)) {
      remoteUrl = remoteUrl.replace(GIT_DIR, "/tree/" + props.getProperty(GIT_COMMIT_TAG));
    } else {
      remoteUrl = remoteUrl.replace(GIT_DIR, "/commit/" + props.getProperty(GIT_COMMIT_ID));
    }

    props.put(GIT_GIT_HUB_URL, remoteUrl);
    if (remoteUrl.indexOf("/tree/") > -1) {
      props.put(GIT_GIT_LABEL, "tags/" + props.getProperty(GIT_COMMIT_TAG));
    } else {
      props.put(GIT_GIT_LABEL, repository.getBranch() + "/" + props.getProperty(GIT_COMMIT_ID));
    }

    OutputStream out = null;

    try {
      out = new FileOutputStream(outputFile);
      props.store(out, " created by " + GitInfo.class.getName());
    } finally {
      IOUtils.closeQuietly(out);
    }

  }

  /**
   * Utility classes should not have a public or default constructor.
   */
  private GitInfo() {
    super();
  }
}
