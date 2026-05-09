package iffelow.jira.ui.ui;

import com.codeborne.selenide.Selenide;
import config.ConfigReader;
import ifellow.jira.ui.CreateBugPage;
import ifellow.jira.ui.IssuePage;
import ifellow.jira.ui.LoginPage;
import ifellow.jira.ui.TestProjectPage;

import iffelow.jira.ui.WebHooks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;

public class CreateBugPageTest extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final TestProjectPage testProjectPage = new TestProjectPage();
    private final CreateBugPage createBugPage = new CreateBugPage();
    private final IssuePage issuePage = new IssuePage();

    @Test
    @DisplayName("Создание бага и перевод в статус 'Готово'")
    void createBugAndCompleteTest() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.login(username, password);
        testProjectPage.openTestProject();
        createBugPage.clickCreateIssue();
        createBugPage.createBug();
        String createdIssueLink = createBugPage.getCreatedIssueLink();
        if (createdIssueLink == null) {
            throw new AssertionError("Ссылка на созданную задачу не получена");
        }
        Selenide.open(createdIssueLink);
        issuePage.shouldBeAtIssuePage();

        String initialStatus = issuePage.getIssueStatus();
        if (initialStatus == null) {
            throw new AssertionError("Не удалось получить статус задачи");
        }

        createBugPage.completeIssue();
        createBugPage.waitForStatusChange("ГОТОВО", Duration.ofSeconds(15));
    }
}