package iffelow.jira.ui.ui;

import com.codeborne.selenide.Selenide;
import config.ConfigReader;
import ifellow.jira.ui.CreateBugPage;
import ifellow.jira.ui.IssuePage;
import ifellow.jira.ui.LoginPage;
import ifellow.jira.ui.TestProjectPage;
import iffelow.jira.ui.WebHooks;
import org.junit.jupiter.api.Assertions;
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
        testProjectPage.clickCreateIssue();

        createBugPage.createBug();

        Assertions.assertTrue(createBugPage.isIssueCreated(),
                "Баг не был создан успешно");

        String createdIssueLink = createBugPage.getCreatedIssueLink();
        Assertions.assertNotNull(createdIssueLink, "Нет ссылки на созданную задачу");

        Selenide.open(createdIssueLink);

        Assertions.assertTrue(issuePage.isAtIssuePage(),
                "Не открылась страница созданной задачи.");

        String initialStatus = issuePage.getIssueStatus();
        Assertions.assertNotNull(initialStatus, "Не удалось получить статус задачи");

        issuePage.completeIssue();

        issuePage.waitForStatusChange("ГОТОВО", Duration.ofSeconds(15));

        String finalStatus = issuePage.getIssueStatus();
        Assertions.assertEquals("ГОТОВО", finalStatus,
                "Задача не завершена. Статус: " + finalStatus);
    }
}
