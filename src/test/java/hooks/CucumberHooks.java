package hooks;

import com.codeborne.selenide.Configuration;
import config.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.*;

public class CucumberHooks {

    @BeforeAll
    public static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        Configuration.pageLoadStrategy = PageLoadStrategy.EAGER.toString();
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.pageLoadTimeout = 15_000;
    }

    @AfterAll
    public static void tearDownAll() {

    }

    @Before
    public void initBrowser() {
        String baseUrl = ConfigReader.get("base.url");
        open(baseUrl);
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @After
    public void afterTest() {
        closeWebDriver();
    }
}