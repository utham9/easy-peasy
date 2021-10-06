import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class StandaloneChrome {

    private WebDriver webDriver;
    private URL selenium_hub = new URL("http:localhost:4444/wd/hub");

    public StandaloneChrome() throws MalformedURLException {
    }

    @BeforeClass
    public void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        dc.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
        webDriver = new RemoteWebDriver(selenium_hub, dc);
        FluentWait wait = new FluentWait(webDriver);
        wait.withTimeout(Duration.ofMillis(5000));
        wait.pollingEvery(Duration.ofMillis(250));
        wait.ignoring(NoSuchElementException.class);
    }

    @Test
    public void sout() {
        webDriver.get("https://www.google.com/");
        WebElement search = webDriver.findElement(By.xpath("//input[@title='Search']"));
        search.sendKeys("jarvis investment");
        search.sendKeys(Keys.ENTER);
        WebElement jarvisLink = webDriver.findElement(By.xpath("//h3[normalize-space()='Jarvis Invest : Wisdom meets science']"));
        jarvisLink.click();
        WebElement jarvisLogo = webDriver.findElement(By.xpath("//img[@alt='Jarvis Logo']"));
        Assert.assertNotNull(jarvisLogo);
    }

    @AfterClass
    public void tearDown(){
        webDriver.close();
        webDriver.quit();
    }
}
