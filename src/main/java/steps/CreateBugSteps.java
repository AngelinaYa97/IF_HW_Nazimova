package steps;

import com.codeborne.selenide.Selenide;
import ifellow.jira.ui.CreateBugPage;
import ifellow.jira.ui.IssuePage;
import ifellow.jira.ui.TestProjectPage;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

public class CreateBugSteps {

    private final TestProjectPage testProjectPage = new TestProjectPage();
    private final CreateBugPage createBugPage = new CreateBugPage();
    private final IssuePage issuePage = new IssuePage();

    private String createdIssueLink;

    @Когда("я нажимаю на кнопку создания задачи")
    public void clickCreateIssueButton() {
        testProjectPage.clickCreateIssue();
    }

    @И("я создаю баг с заполнением всех полей")
    public void createNewBug() {
        createBugPage.createBug();
    }

    @Тогда("баг создан успешно")
    public void verifyBugCreated() {
        Assertions.assertTrue(createBugPage.isIssueCreated(),
                "Баг не был создан успешно");
    }

    @И("я получаю ссылку на созданную задачу")
    public void saveCreatedIssueLink() {
        createdIssueLink = createBugPage.getCreatedIssueLink();
        Assertions.assertNotNull(createdIssueLink, "Нет ссылки на созданную задачу");
    }

    @Когда("я открываю страницу созданной задачи")
    public void openCreatedIssuePage() {
        Selenide.open(createdIssueLink);
    }

    @Тогда("открылась страница созданной задачи")
    public void verifyAtCreatedIssuePage() {
        Assertions.assertTrue(issuePage.isAtIssuePage(),
                "Не открылась страница созданной задачи.");
    }

    @И("зафиксирован начальный статус задачи")
    public void rememberInitialStatus() {
        String initialStatus = issuePage.getIssueStatus();
        Assertions.assertNotNull(initialStatus, "Не удалось получить статус задачи");
    }

    @Когда("я перевожу задачу в завершенный статус")
    public void completeIssue() {
        issuePage.completeIssue();
    }

    @И("жду смену статуса на {string} в течение {int} секунд")
    public void waitForStatusChange(String expectedStatus, int seconds) {
        issuePage.waitForStatusChange(expectedStatus, Duration.ofSeconds(seconds));
    }

    @Тогда("статус задачи {string}")
    public void verifyIssueStatus(String expectedStatus) {
        String finalStatus = issuePage.getIssueStatus();
        Assertions.assertEquals(expectedStatus, finalStatus,
                "Задача не завершена. Статус: " + finalStatus);

    }
}