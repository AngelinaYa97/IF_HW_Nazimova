package steps;

import com.codeborne.selenide.Selenide;
import config.ConfigReader;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

public class LoginSteps {

    private final TestContext context = TestContext.getInstance();

    @Дано("я открываю страницу авторизации")
    public void openLoginPage() {
        Selenide.open(ConfigReader.get("base.url"));
    }

    @Когда("я ввожу логин и пароль")
    public void enterLoginAndPassword() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");
        context.loginPage.login(username, password);
    }

    @Тогда("URL страницы содержит {string}")
    public void verifyUrlContains(String expectedUrlPart) {
        String currentUrl = Selenide.webdriver().driver().url();
        assert currentUrl.toLowerCase().contains(expectedUrlPart.toLowerCase()) :
                "URL должен содержать '" + expectedUrlPart + "', но текущий URL: " + currentUrl;
    }
}