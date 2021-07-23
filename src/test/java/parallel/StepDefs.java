package parallel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StepDefs {

    public WebDriver driver;
    public WebDriverRunner driverSelenide;
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
    public static ThreadLocal<WebDriverRunner> tdriverSelenide = new ThreadLocal<WebDriverRunner>();


    public static synchronized WebDriver getDriver() {
        return tdriver.get();
    }
    public static synchronized WebDriverRunner getSeleideDriver() {
        return tdriverSelenide.get();
    }

    public WebDriver initialize_driver(String browser) {

        if (browser.toLowerCase().contains("ie")) {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            System.setProperty("WebDriver.ie.driver", "src/test/resources/IEDriverServer.exe");
            driver = new InternetExplorerDriver(capabilities);
            driver.manage().deleteAllCookies();
//            driver.manage().window().fullscreen();
            tdriver.set(driver);

        } else if (browser.toLowerCase().contains("firefox")) {

            System.setProperty("webdriver.gecko.driver","src/test/resources/geckodriver.exe");
            driver = new FirefoxDriver();

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
//            driver.manage().window().fullscreen();
            tdriver.set(driver);
        }

        else if(browser.toLowerCase().contains("chrome")){

            System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
            driver = new ChromeDriver();

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
//            driver.manage().window().fullscreen();
            tdriver.set(driver);
        }
        return getDriver();
    }

    public WebDriverRunner initialize_driver_selenide(String browser) {

        if (browser.toLowerCase().contains("ie")) {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            System.setProperty("WebDriver.ie.driver", "src/test/resources/IEDriverServer.exe");
            Configuration.browser = "ie";
            WebDriverRunner.setWebDriver(new InternetExplorerDriver(capabilities));
            driverSelenide = new WebDriverRunner();
            tdriverSelenide.set(driverSelenide);

        } else if (browser.toLowerCase().contains("firefox")) {
            Configuration.browser = "firefox";
            System.setProperty("webdriver.gecko.driver","src/test/resources/geckodriver.exe");
            WebDriverRunner.setWebDriver(new FirefoxDriver());
            driverSelenide = new WebDriverRunner();
            tdriverSelenide.set(driverSelenide);
        }

        else if(browser.toLowerCase().contains("chrome")){
            Configuration.browser = "chrome";
            System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
            WebDriverRunner.setWebDriver(new ChromeDriver());
            driverSelenide = new WebDriverRunner();
            tdriverSelenide.set(driverSelenide);
        }
        return getSeleideDriver();
    }

    @Given("Step from {string} in {string} feature file")
    public void step(String scenario, String file) throws InterruptedException {
        System.out.format("Thread ID - %2d - %s from %s feature file.\n",
                Thread.currentThread().getId(), scenario,file);

    }

    @Step("open URL in the browser")
    @And("^open url \"(.*)\" in \"(.*)\" browser$")
    public void openUrl(String url, String browser) {
//        initialize_driver(browser);
        initialize_driver_selenide(browser);
        open(url);
//        driver.get(url);
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Step("set language to English")
    @And("^set language to English$")
    public void setLanguage() {
        $(By.xpath("//a[text()='English']")).shouldBe(Condition.visible, Duration.ofSeconds(30)).click();
//        driver.findElement(By.xpath("//a[text()='English']")).click();
    }

    @Step("type search phrase to the input field")
    @And("^type text \"(.*)\" in search field$")
    public void searchInput(String text) {
        $(By.xpath("//input[@aria-label='Search']")).clear();
        $(By.xpath("//input[@aria-label='Search']")).sendKeys(text);
        $(By.xpath("//input[@aria-label='Search']")).sendKeys(Keys.ENTER);
//        driver.findElement(By.xpath("//input[@aria-label='Search']")).clear();
//        driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(text);
//        driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(Keys.ENTER);
    }

    @Step("fail the step")
    @And("^fail step$")
    public void failStep() {
        $(By.xpath("//input[@aria-label='343324']")).clear();
//        driver.findElement(By.xpath("//input[@aria-label='343324']")).clear();
    }

    @Step("wait")
    @And("^wait for \"(.*)\" seconds$")
    public void driverWaitSeconds(int time) throws InterruptedException {
        Thread.sleep(time);
    }



}
