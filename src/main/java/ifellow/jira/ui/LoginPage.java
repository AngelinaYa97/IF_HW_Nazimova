package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.SetValueOptions.withText;

public class LoginPage {

    private final SelenideElement usernameField = $x("//input[@id='login-form-username']")
            .as("Поле ввода логина");
    private final SelenideElement passwordField = $x("//input[@id='login-form-password']")
            .as("Поле ввода пароля");
    private final SelenideElement loginButton = $x("//input[@id='login']")
            .as("Кнопка входа");

    @Step("Ввести логин пользователя")
    public void enterUsername(String username) {
        usernameField.setValue(username);
    }

    @Step("Ввести пароль")
    public void enterPassword(@Param(mode = Parameter.Mode.MASKED) String password) {
        passwordField.shouldBe(Condition.visible)
                .setValue(withText(password).sensitive());
    }

    @Step("Нажать на кнопку \"Вход\"")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Проверить, что пользователь авторизован")
    public void verifyLoginSuccess() {
        Selenide.webdriver().shouldHave(WebDriverConditions.urlContaining("Dashboard"));
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        verifyLoginSuccess();
    }
}
