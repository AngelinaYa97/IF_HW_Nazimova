package iffelow.jira.ui;

import com.codeborne.selenide.Configuration;
import config.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import static com.codeborne.selenide.Selenide.*;
import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class WebHooks {

    @BeforeAll
    public static void setup() {
        Configuration.baseUrl = ConfigReader.get("base.url");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";
        Configuration.pageLoadTimeout = 15_000;
        Configuration.screenshots = true;
        Configuration.savePageSource = true;

        addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @BeforeEach
    public void initBrowser() {
        String baseUrl = ConfigReader.get("base.url");
        open(baseUrl);
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @AfterEach
    public void afterTest() {
        closeWebDriver();
    }
}
