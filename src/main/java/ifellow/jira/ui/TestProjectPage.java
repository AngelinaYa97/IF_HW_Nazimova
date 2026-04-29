package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class TestProjectPage {
    private final CreateBugPage createBugPage = new CreateBugPage();
    private final SelenideElement projectsMenu = $x("//*[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement testProject = $x("//*[@id='admin_main_proj_link_lnk']").as("Проект Test");
    private final SelenideElement createIssueButton = $x("//*[@id='create_link']").as("Кнопка создания задачи");
    private final SelenideElement issueCounter = $x("//div[@class='showing']/span").as("Счетчик задач");

    @Step("Открыть проект Test")
    public void openTestProject() {
        projectsMenu.click();
        testProject.click();
    }

    @Step("Проверить, что открыт проект Test")
    public boolean isAtTestProject() {
        return Selenide.webdriver().driver().url().contains("TEST");
    }

    @Step("Получить общее количество задач в проекте")
    public int getTotalIssuesCount() {
        String counterText = issueCounter.shouldBe(Condition.visible).getText();
        String[] parts = counterText.split("из");
        return Integer.parseInt(parts[1].trim());
    }

    @Step("Нажать кнопку создания задачи")
    public void clickCreateIssue() {
        createIssueButton.click();
    }
    @Step("Создать баг (заполнить все поля и отправить)")
    public void createBug(){
        createBugPage.createBug();
    }

    @Step("Проверить, что количество задач увеличилось на 1 (было {initialCount})")
    public void verifyIssuesCountIncreasedByOne(int initialCount) {
        int finalCount = getTotalIssuesCount();
        int actualIncrease = finalCount - initialCount;
        if (actualIncrease != 1) {
            throw new AssertionError("Количество задач не увеличилось на 1. " +
                    "Было: " + initialCount + ", Стало: " + finalCount + ", Увеличение: " + actualIncrease);
        }
    }
}