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
    @DisplayName("Проверка счетчика задач после создания новой задачи")
    void openTestProjectAndCheckCounterTest() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.login(username, password);
        testProjectPage.openTestProject();

        Assertions.assertTrue(testProjectPage.isAtTestProject(), "Проект Test не открыт");

        int initialIssuesCount = testProjectPage.getTotalIssuesCount();
        Assertions.assertTrue(initialIssuesCount > 0, "Количество задач должно быть положительным");

        testProjectPage.clickCreateIssue();
        testProjectPage.createBug();

        testProjectPage.openTestProject();
        Assertions.assertTrue(testProjectPage.isAtTestProject(), "Не удалось вернуться в проект Test");

        testProjectPage.verifyIssuesCountIncreasedByOne(initialIssuesCount);
    }
}