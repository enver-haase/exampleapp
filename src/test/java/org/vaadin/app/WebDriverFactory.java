package org.vaadin.app;

import com.vaadin.testbench.TestBench;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This singleton class allows test classes, particularly those deriving from {@link AbstractIT}, to not need to configure the test
 * environment any further. Here the configuration in {@code testing.properties} is being loaded and parsed and the corresponding
 * WebDriver is being created, and wrapped with a TestBench proxy.
 */
public class WebDriverFactory {
  private static final Logger LOGGER = Logger.getAnonymousLogger();
  private static final String TEST_PROPS_FILENAME = "src/test/resources/testing.properties";
  private static final String PROP_WEBDRIVER = "selenium.webdriver";
  private static final String DEFAULT_WEBDRIVER = "Firefox";
  private static final WebDriverFactory INSTANCE = new WebDriverFactory();

  private final String webDriverName;

  private WebDriverFactory() {
    final Properties properties = new Properties();
    final File file = new File(TEST_PROPS_FILENAME);
    try (InputStream input = new FileInputStream(file)) {
      properties.load(input);
      LOGGER.info("Properties file '" + file.getAbsolutePath() + "' loaded okay.");
      final Set<Object> keys = properties.keySet();
      for (Object key : keys.toArray()) {
        LOGGER.info(
            "Properties file '" + file.getAbsolutePath() + "', found key '" + key.toString() + "'.");
      }
    } catch (IOException | NullPointerException exc) {
      LOGGER.log(Level.SEVERE, "Could not load properties file '" + file.getAbsolutePath() + "': "+exc.getMessage(), exc);
    }

    this.webDriverName = properties.getProperty(PROP_WEBDRIVER, DEFAULT_WEBDRIVER);
    LOGGER.info(
        "WebDriver for '"
            + this.webDriverName
            + "' chosen through property '"
            + PROP_WEBDRIVER
            + "'.");
  }

  public static WebDriverFactory getInstance() {
    return INSTANCE;
  }

  public WebDriver createDriver() {
    if (webDriverName.equalsIgnoreCase("chrome")) {
      return TestBench.createDriver(new ChromeDriver());
    } else if (webDriverName.equalsIgnoreCase("firefox")) {
      return TestBench.createDriver(new FirefoxDriver());
    } else if (webDriverName.equalsIgnoreCase("edge")) {
      return TestBench.createDriver(new EdgeDriver());
    } else if (webDriverName.equalsIgnoreCase("opera")) {
      return TestBench.createDriver(new OperaDriver());
    } else if (webDriverName.equalsIgnoreCase("internetexplorer")) {
      return TestBench.createDriver(new InternetExplorerDriver());
    } else if (webDriverName.equalsIgnoreCase("safari")) {
      return TestBench.createDriver(new SafariDriver());
    }
    throw new IllegalArgumentException(
        "At this time I do not understand '" + webDriverName + "', extend me.");
  }
}
