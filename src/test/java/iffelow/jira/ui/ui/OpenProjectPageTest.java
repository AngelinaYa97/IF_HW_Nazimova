package iffelow.jira.ui.ui;

import config.ConfigReader;
import ifellow.jira.ui.LoginPage;
import ifellow.jira.ui.OpenProjectPage;
import iffelow.jira.ui.WebHooks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OpenProjectPageTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final OpenProjectPage openProjectPage = new OpenProjectPage();

    @Test
    @DisplayName("Открытие проекта 'TEST'")
    void openProjectTest() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.login(username, password);
        openProjectPage.openProject("TEST");

        String currentUrl = com.codeborne.selenide.Selenide.webdriver().driver().url();
        Assertions.assertTrue(currentUrl.contains("TEST"),
                "Проект Test не открыт.");
    }
}
