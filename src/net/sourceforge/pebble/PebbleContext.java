/*
 * Copyright (c) 2003-2006, Simon Brown
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   - Neither the name of Pebble nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.sourceforge.pebble;

import net.sourceforge.pebble.util.RelativeDate;
import net.sourceforge.pebble.security.PebbleUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * A bean representing the Pebble context.
 *
 * @author    Simon Brown
 */
public class PebbleContext {

  /** the log used by this class */
  private static Log log = LogFactory.getLog(PebbleContext.class);

  private Configuration configuration;

  private PebbleUserDetailsService pebbleUserDetailsService;

  private String buildVersion;
  private String buildDate;

  private static final String BUILD_VERSION_KEY = "build.version";
  private static final String BUILD_DATE_KEY = "build.date";

  /** the time that Pebble was started */
  private Date startTime;

  /** the directory where Pebble is deployed */
  private String webApplicationRoot;

  private static final PebbleContext instance = new PebbleContext();

  public static PebbleContext getInstance() {
    return instance;
  }

  private PebbleContext() {
    // and note when Pebble was started
    this.startTime = new Date();

    try {
      Properties buildProperties = new Properties();
      InputStream in = getClass().getClassLoader().getResourceAsStream("pebble-build.properties");
      if (in != null) {
        buildProperties.load(in);
        this.buildVersion = buildProperties.getProperty(BUILD_VERSION_KEY);
        this.buildDate = buildProperties.getProperty(BUILD_DATE_KEY);
        in.close();
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      e.printStackTrace();
    }
  }

  /**
   * Gets the version of Pebble being used.
   *
   * @return    the version number as a String
   */
  public String getBuildVersion() {
    return this.buildVersion;
  }

  /**
   * Gets the date that Pebble was built.
   *
   * @return    the date as a String
   */
  public String getBuildDate() {
    return this.buildDate;
  }

  /**
   * Gets the amount of time that Pebble has been running for.
   *
   * @return  a number of milliseconds
   */
  public RelativeDate getUptime() {
    return new RelativeDate(new Date().getTime() - startTime.getTime());
  }

  public long getMemoryUsageInKB() {
    return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
  }

  public long getTotalMemoryInKB() {
    return Runtime.getRuntime().totalMemory() / 1024;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public PebbleUserDetailsService getPebbleUserDetailsService() {
    return pebbleUserDetailsService;
  }

  public void setPebbleUserDetailsService(PebbleUserDetailsService pebbleUserDetailsService) {
    this.pebbleUserDetailsService = pebbleUserDetailsService;
  }
  
  /**
   * Sets the directory where themes are located.
   *
   * @param webApplicationRoot    the absolute path to the webapp root on disk
   */
  public void setWebApplicationRoot(String webApplicationRoot) {
    this.webApplicationRoot = webApplicationRoot;
  }

  public String getWebApplicationRoot() {
    return webApplicationRoot;
  }

}