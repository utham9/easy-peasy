import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;
import com.ep.report.testng.TestNGReporter;
import com.ep.report.testng.TestNgReportListener;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.function.Function;

//@Listeners(TestNgReportListener.class)
public class StandaloneChrome {

  private WebDriver webDriver;
  private URL selenium_hub = new URL("http://localhost:4444/wd/hub");
  private FluentWait fluentWait;

  public StandaloneChrome() throws MalformedURLException {}

  @BeforeClass
  public void setup() {
    ChromeOptions chromeOptions = new ChromeOptions();
    // chromeOptions.setHeadless(true);
    chromeOptions.addArguments("--start-maximized");
    chromeOptions.addArguments("--verbose");
    chromeOptions.addArguments("--disable-dev-shm-usage");
    DesiredCapabilities dc = DesiredCapabilities.chrome();
    dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    webDriver = new RemoteWebDriver(selenium_hub, dc);
    fluentWait = new FluentWait(webDriver);
    fluentWait.withTimeout(Duration.ofMillis(5000));
    fluentWait.pollingEvery(Duration.ofMillis(250));
    fluentWait.ignoring(NoSuchElementException.class);
  }

  @Test
  public void sout() {

    webDriver.get("https://www.google.com/");
    WebElement search = getElement(By.xpath("//input[@title='Search']"));
    search.sendKeys("jarvis investment");
    search.sendKeys(Keys.ENTER);
    WebElement jarvisLink =
        getElement(By.xpath("//h3[normalize-space()='Jarvis Invest : Wisdom meets science']"));
    jarvisLink.click();
    // WebElement jarvisLogo = getElement(By.xpath("//img[@alt='Jarvis Logo']"));
    TestNGReporter.screenshot(webDriver, "jarvis home page");
    // Assert.assertNotNull(jarvisLogo);
  }

  @Test
  public void sout2() {

    webDriver.get("https://www.google.com/");
    WebElement search = getElement(By.xpath("//input[@title='Search']"));
    search.sendKeys("jarvis investment");
    search.sendKeys(Keys.ENTER);
    WebElement jarvisLink =
            getElement(By.xpath("//h3[normalize-space()='Jarvis Invest: Buy Best Artificial Intelligence Stocks In India']"));
    jarvisLink.click();
    // WebElement jarvisLogo = getElement(By.xpath("//img[@alt='Jarvis Logo']"));
   TestNGReporter.screenshot(webDriver, "jarvis home page");
    // Assert.assertNotNull(jarvisLogo);
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



  @AfterClass
  public void tearDown() {
    webDriver.close();
    webDriver.quit();
  }
}
