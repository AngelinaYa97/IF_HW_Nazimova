package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private final SelenideElement usernameField = $x("//input[@id='login-form-username']")
            .as("Поле ввода логина");
    private final SelenideElement passwordField = $x("//input[@id='login-form-password']")
            .as("Поле ввода пароля");
    private final SelenideElement loginButton = $x("//input[@id='login']")
            .as("Кнопка входа");

    public void login(String username, String password) {
        usernameField.shouldBe(Condition.visible).setValue(username);
        usernameField.shouldHave(Condition.value(username));

        passwordField.shouldBe(Condition.visible).setValue(password);
        passwordField.shouldHave(Condition.value(password));

        loginButton.shouldBe(Condition.visible, Condition.enabled).click();
    }
}
