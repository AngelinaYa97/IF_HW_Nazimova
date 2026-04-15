package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IssueSteps {

    private final TestContext context = TestContext.getInstance();

    @Когда("я выполняю поиск задачи {string}")
    public void openIssue(String issueKey) {
        context.testProjectPage.openIssueByKey(issueKey);
    }

    @Тогда("открыта страница задачи")
    public void verifyAtIssuePage() {
        assertTrue(context.issuePage.isAtIssuePage(),
                "Не удалось открыть страницу задачи");
    }

    @И("статус задачи равен {string}")
    public void verifyIssueStatus(String expectedStatus) {
        String actualStatus = context.issuePage.getIssueStatus();
        assertEquals(expectedStatus, actualStatus,
                "Статус задачи не соответствует ожидаемому. " +
                        "Ожидается: '" + expectedStatus + "', Получено: '" + actualStatus + "'");
    }

    @И("поле {string} содержит {string}")
    public void verifyFieldContains(String fieldName, String expectedValue) {
        String actualFixVersions = context.issuePage.getFixVersions();
        assertTrue(actualFixVersions.contains(expectedValue),
                "Версия в поле '" + fieldName + "' не соответствует ожидаемой. " +
                        "Ожидается: '" + expectedValue + "', Получено: '" + actualFixVersions + "'");
    }
}