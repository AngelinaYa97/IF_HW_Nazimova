package iffelow.jira.ui.ui;

import config.ConfigReader;
import ifellow.jira.ui.CreateBugPage;
import ifellow.jira.ui.LoginPage;
import ifellow.jira.ui.TestProjectPage;
import iffelow.jira.ui.WebHooks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestProjectPageTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final TestProjectPage testProjectPage = new TestProjectPage();
    private final CreateBugPage createBugPage = new CreateBugPage();

    @Test
    @DisplayName("Проверка счетчика задач")
    void openTestProjectAndCheckCounterTest() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.login(username, password);
        testProjectPage.openTestProject();

        Assertions.assertTrue(testProjectPage.isAtTestProject(),
                "Проект Test не открыт. Текущий URL: " + com.codeborne.selenide.Selenide.webdriver().driver().url());

        int initialIssuesCount = testProjectPage.getTotalIssuesCount();
        Assertions.assertTrue(initialIssuesCount > 0,
                "Количество задач должно быть положительным числом. Получено: " + initialIssuesCount);

        testProjectPage.clickCreateIssue();
        createBugPage.createBug();

        Assertions.assertTrue(createBugPage.isIssueCreated(),
                "Задача не была создана");

        testProjectPage.openTestProject();
        Assertions.assertTrue(testProjectPage.isAtTestProject(),
                "Не удалось вернуться в проект Test");

        int finalIssuesCount = testProjectPage.getTotalIssuesCount();
        Assertions.assertEquals(initialIssuesCount + 1, finalIssuesCount,
                "Количество задач не увеличилось на 1 после создания задачи. " +
                        "Было: " + initialIssuesCount + ", Стало: " + finalIssuesCount);

    }
}
