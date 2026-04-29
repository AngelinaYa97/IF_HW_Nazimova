package iffelow.jira.ui.ui;

import config.ConfigReader;
import ifellow.jira.ui.IssuePage;
import ifellow.jira.ui.LoginPage;
import ifellow.jira.ui.TestProjectPage;
import iffelow.jira.ui.WebHooks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IssuePageTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final TestProjectPage testProjectPage = new TestProjectPage();
    private final IssuePage issuePage = new IssuePage();

    @Test
    @DisplayName("Проверка статуса и версии задачи TestSeleniumATHomework")
    void checkIssueStatusAndVersionTest() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");
        String issueKey = "TestSeleniumATHomework";

        loginPage.login(username, password);
        testProjectPage.openTestProject();
        issuePage.openIssueByKey(issueKey);   // поиск по названию

        issuePage.shouldBeAtIssuePage();      // проверка, что страница задачи открыта
        issuePage.shouldHaveStatusToDo();
        issuePage.shouldHaveFixVersion();
        String actualStatus = issuePage.getIssueStatus();
        Assertions.assertEquals("СДЕЛАТЬ", actualStatus, "Статус задачи не соответствует ожидаемому");

        String actualFixVersions = issuePage.getFixVersions();
        Assertions.assertTrue(actualFixVersions.contains("Version 2.0"),
                "Версия в поле 'Исправить в версиях' не соответствует ожидаемой");
    }
}