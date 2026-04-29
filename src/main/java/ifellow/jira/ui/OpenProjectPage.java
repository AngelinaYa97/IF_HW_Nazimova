package ifellow.jira.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class OpenProjectPage {

    private final SelenideElement projectMenu = $x("//*[@id='browse_link']").as("Drop-down 'Проекты'");
    private final SelenideElement testProject = $x("//*[@id='admin_main_proj_link_lnk']").as("Проект Test");

    @Step("Открыть выпадающее меню 'Проекты'")
    public void openProjectMenu() {
        projectMenu.click();
    }

    @Step("Выбрать проект 'Test' в меню")
    public void selectTestProject() {
        testProject.click();

    }
    @Step("Проверить, что открыта страница проекта Test")
    public void verifyProjectPageOpened() {
        Selenide.webdriver().shouldHave(WebDriverConditions.urlContaining("TEST"));
    }
}