package steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestProjectSteps {

    private final TestContext context = TestContext.getInstance();
    private int initialIssuesCount;

    @Тогда("я нахожусь на странице проекта {string}")
    public void verifyAtProjectPage(String projectName) {
        assertTrue(context.testProjectPage.isAtTestProject(),
                "Проект " + projectName + " не открыт. Текущий URL: " + Selenide.webdriver().driver().url());
    }

    @Когда("зафиксировано текущее количество задач")
    public void rememberCurrentIssueCount() {
        initialIssuesCount = context.testProjectPage.getTotalIssuesCount();
    }

    @Тогда("количество задач больше 0")
    public void verifyIssueCountPositive() {
        Assertions.assertTrue(initialIssuesCount > 0,
                "Количество задач должно быть положительным числом. Получено: " + initialIssuesCount);
    }

    @Когда("я нажимаю кнопку создания задачи")
    public void clickCreateIssueButton() {
        context.testProjectPage.clickCreateIssue();
    }

    @И("создаю баг с заполнением всех полей")
    public void createNewBug() {
        context.createBugPage.createBug();
    }

    @Тогда("задача успешно создана")
    public void verifyIssueCreated() {
        assertTrue(context.createBugPage.isIssueCreated(),
                "Задача не была создана");
    }

    @Когда("я возвращаюсь в проект {string}")
    public void returnToProject(String projectName) {
        context.testProjectPage.openTestProject();
    }

    @Тогда("количество задач увеличилось на 1")
    public void verifyIssueCountIncreasedByOne() {
        int finalIssuesCount = context.testProjectPage.getTotalIssuesCount();
        Assertions.assertEquals(initialIssuesCount + 1, finalIssuesCount,
                "Количество задач не увеличилось на 1. Было: " + initialIssuesCount +
                        ", Стало: " + finalIssuesCount);
    }
}