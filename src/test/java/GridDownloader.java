import com.ep.utils.ChromeUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class GridDownloader {

  private WebDriver webDriver;
  private final URL selenium_hub = new URL("http://localhost:4444/wd/hub");
  private FluentWait fluentWait;

  public GridDownloader() throws MalformedURLException {}

  @BeforeClass
  public void setup() {
    ChromeOptions chromeOptions = new ChromeOptions();
    // chromeOptions.setHeadless(true);
    chromeOptions.addArguments("--start-maximized");
    chromeOptions.addArguments("--verbose");
    chromeOptions.addArguments("--disable-dev-shm-usage");
    HashMap chromePrefs = new HashMap();
    chromePrefs.put("download.prompt_for_download", Boolean.valueOf(false));
    chromePrefs.put("plugins.always_open_pdf_externally", Boolean.valueOf(true));
    chromePrefs.put("safebrowsing_for_trusted_sources_enabled", Boolean.valueOf(false));
    chromeOptions.setExperimentalOption("prefs", chromePrefs);
    DesiredCapabilities dc = DesiredCapabilities.chrome();
    dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    webDriver = new RemoteWebDriver(selenium_hub, dc);
    fluentWait = new FluentWait(webDriver);
    fluentWait.withTimeout(Duration.ofMillis(5000));
    fluentWait.pollingEvery(Duration.ofMillis(250));
    fluentWait.ignoring(NoSuchElementException.class);
  }

  @Test
  public void sout() throws Exception {
    webDriver.get(
        "https://chromedriver.storage.googleapis.com/index.html?path=97.0.4692.71/#:~:text=chromedriver_mac64.zip");
    getElement(By.linkText("chromedriver_mac64.zip")).click();
    Thread.sleep(60000);
    ArrayList downloaded_files = ChromeUtils.getDownloadedFiles((RemoteWebDriver) webDriver);
    downloaded_files.forEach(System.out::println);
    String file_content =
        ChromeUtils.getFileContent((RemoteWebDriver) webDriver, (String) downloaded_files.get(0));
    System.out.println(file_content);
  }


  private WebElement getElement(By locator) {
    return (WebElement)
        fluentWait.until(
            new Function() {
              @Override
              public Object apply(Object o) {
                return webDriver.findElement(locator);
              }
            });
  }

  private void takeSnapShot(WebDriver webdriver, String fileWithPath) {
    TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
    File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
    File DestFile = new File(fileWithPath);
    try {
      FileUtils.copyFile(SrcFile, DestFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @AfterClass
  public void tearDown() {
    webDriver.close();
    webDriver.quit();
  }
}
