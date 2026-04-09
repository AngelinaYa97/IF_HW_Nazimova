package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class IssuePage {

    private final SelenideElement issueStatus = $x("//*[@id='status-val']").as("Статус задачи");
    private final SelenideElement fixVersionsField = $x("//*[@id='fixVersions-field']").as("Поле 'Исправить в версиях'");
    private final SelenideElement workflowButton = $x("//*[@id='opsbar-transitions_more']").as("Кнопка 'Бизнес-процесс'");
    private final SelenideElement workflowDropdown = $x("//*[@id='opsbar-transitions_more_drop']").as("Выпадающее меню бизнес-процесса");
    private final SelenideElement doneTransition = $x("//aui-item-link[@id='action_id_31']").as("Кнопка 'Выполнено'");

    public String getIssueStatus() {
        return issueStatus.shouldBe(Condition.visible, Duration.ofSeconds(10)).getText().trim();
    }

    public String getFixVersions() {
        return fixVersionsField.shouldBe(Condition.visible, Duration.ofSeconds(10)).getText().trim();
    }

    public boolean isAtIssuePage() {
        return Selenide.webdriver().driver().url().contains("/browse/");
    }

    public void completeIssue() {
        workflowButton.shouldBe(Condition.visible, Condition.enabled).click();
        workflowDropdown.shouldBe(Condition.visible, Duration.ofSeconds(10));
        doneTransition.shouldBe(Condition.visible, Condition.enabled).click();
    }
    public void waitForStatusChange(String expectedStatus, Duration timeout) {
        issueStatus.shouldHave(Condition.text(expectedStatus), timeout);
    }
}
