package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class IssuePage {

    private final SelenideElement searchInput = $x("//*[@id='quickSearchInput']").as("Поле поиска");
    private final SelenideElement issueStatus = $x("//*[@id='status-val']").as("Статус задачи");
    private final SelenideElement fixVersionsField = $x("//*[@id='fixVersions-field']").as("Поле 'Исправить в версиях'");


    @Step("Выполнить поиск задачи {issueKey}")
    public void openIssueByKey(String issueKey) {
        searchInput.shouldBe(Condition.visible).clear();
        searchInput.setValue(issueKey).pressEnter();
        Selenide.webdriver().shouldHave(WebDriverConditions.urlContaining("/browse/"));
    }

    @Step("Проверить, что открыта страница задачи")
    public void shouldBeAtIssuePage() {
        Selenide.webdriver().shouldHave(WebDriverConditions.urlContaining("/browse/"));
    }

    @Step("Получить текущий статус задачи")
    public String getIssueStatus() {
        return issueStatus.shouldBe(Condition.visible).getText().trim();
    }
    @Step("Проверить, что статус задачи = 'СДЕЛАТЬ'")
    public void shouldHaveStatusToDo() {
        issueStatus.shouldHave(Condition.text("СДЕЛАТЬ"));
    }
    @Step("Получить текущую версию исправления")
    public String getFixVersions() {
        return fixVersionsField.shouldBe(Condition.visible).getText().trim();
    }
    @Step("Проверить, что версия = 'Version 2.0'")
    public void shouldHaveFixVersion() {
        fixVersionsField.shouldHave(Condition.text("Version 2.0"));
    }
}


