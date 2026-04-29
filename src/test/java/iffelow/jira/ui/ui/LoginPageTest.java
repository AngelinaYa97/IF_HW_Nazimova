package iffelow.jira.ui.ui;

import config.ConfigReader;
import ifellow.jira.ui.LoginPage;
import iffelow.jira.ui.WebHooks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginPageTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();

    @Test
    @DisplayName("Авторизация пользователя в Jira")
    void loginTest() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        loginPage.verifyLoginSuccess();
    }
}
