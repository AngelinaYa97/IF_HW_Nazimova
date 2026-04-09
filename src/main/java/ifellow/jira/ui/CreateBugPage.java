package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CreateBugPage {

    private final SelenideElement summaryField = $x("//*[@id='summary']").as("Поле 'Название'");
    private final SelenideElement descriptionField = $x("//*[@id='description-wiki-edit']").as("Поле 'Описание'");
    private final SelenideElement fixVersionsField = $x("//*[@id='fixVersions']").as("Поле 'Исправить в версиях'");
    private final SelenideElement priorityField = $x("//*[@id='priority-field']").as("Поле 'Приоритет'");
    private final SelenideElement labelsField = $x("//*[@id='labels-textarea']").as("Поле 'Метки'");
    private final SelenideElement environmentField = $x("//*[@id='environment-wiki-edit']").as("Поле 'Окружение'");
    private final SelenideElement versionsField = $x("//*[@id='versions']").as("Поле 'Затронутые версии'");
    private final SelenideElement assigneeField = $x("//*[@id='assignee-field']").as("Поле 'Исполнитель'");
    private final SelenideElement severityField = $x("//*[@id='customfield_10400']").as("Поле 'Severity'");
    private final SelenideElement descriptionVisualTab = $x("//div[@field-id='description']//button[contains(text(), 'Визуальный')]").as("Вкладка визуального редактора описания");
    private final SelenideElement environmentVisualTab = $x("//div[@field-id='environment']//button[contains(text(), 'Визуальный')]").as("Вкладка визуального редактора окружения");
    private final SelenideElement submitButton = $x("//*[@id='create-issue-submit']").as("Кнопка 'Создать'");
    private final SelenideElement successMessage = $x("//*[@id='aui-flag-container']//div[contains(@class, 'success')]").as("Сообщение об успешном создании");
    private final SelenideElement createdIssueLink = $x("//*[@id='aui-flag-container']//a[contains(@href, '/browse/')]").as("Ссылка на созданную задачу");
    private final SelenideElement issueLinkTypeField = $x("//*[@id='issuelinks-linktype']").as("Поле 'Тип связи'");
    private final SelenideElement issueLinkField = $x("//*[@id='issuelinks-issues-textarea']").as("Поле 'Связанные задачи'");
    private final SelenideElement epicLinkField = $x("//*[@id='customfield_10100-field']").as("Поле 'Ссылка на эпик'");
    private final SelenideElement sprintField = $x("//*[@id='customfield_10104-field']").as("Поле 'Спринт'");
    private final SelenideElement descriptionIframe = $x("//div[@field-id='description']//iframe").as("Iframe визуального редактора описания");
    private final SelenideElement environmentIframe = $x("//div[@field-id='environment']//iframe").as("Iframe визуального редактора окружения");
    private final SelenideElement body = $x("//body").as("Тело iframe");
    private final SelenideElement fixVersionsFirstOption = $x("//*[@id='fixVersions']//option[not(@value='-1')][1]").as("Первая опция версий");
    private final SelenideElement priorityFirstOption = $x("//*[@id='priority-suggestions']//li[1]").as("Первая опция приоритета");
    private final SelenideElement versionsFirstOption = $x("//*[@id='versions']//option[not(@value='-1')][1]").as("Первая опция затронутых версий");
    private final SelenideElement assigneeFirstOption = $x("//*[@id='assignee-suggestions']//li[1]").as("Первая опция исполнителя");
    private final SelenideElement severitySecondOption = $x("//*[@id='customfield_10400']//option[2]").as("Вторая опция Severity");
    private final SelenideElement issueLinkTypeFirstOption = $x("//*[@id='issuelinks-linktype']//option[1]").as("Первая опция типа связи");
    private final SelenideElement issueLinkFirstOption = $x("//*[@id='issuelinks-issues-suggestions']//li[1]").as("Первая опция связанных задач");
    private final SelenideElement epicLinkFirstOption = $x("//*[@id='customfield_10100-suggestions']//h5[text()='Предложения']/following-sibling::ul//li[1]").as("Первая опция эпика");
    private final SelenideElement sprintFirstOption = $x("//*[@id='customfield_10104-suggestions']//h5[text()='Предложения']/following-sibling::ul//li[1]").as("Первая опция спринта");

    public void setSummary() {
        summaryField.shouldBe(Condition.visible).clear();
        summaryField.setValue("Тестовый баг");
        summaryField.shouldHave(Condition.value("Тестовый баг"));
    }

    public void setDescription() {
        scrollToElement(descriptionField);
        ensureVisualEditor(descriptionVisualTab);

        Selenide.switchTo().frame(descriptionIframe);
        body.shouldBe(Condition.visible);
        body.setValue("Автотест создал этот баг");
        Selenide.switchTo().defaultContent();
    }

    public void setEnvironment() {
        scrollToElement(environmentField);
        ensureVisualEditor(environmentVisualTab);

        Selenide.switchTo().frame(environmentIframe);
        body.shouldBe(Condition.visible);
        body.setValue("Тестовое окружение");
        Selenide.switchTo().defaultContent();
    }

    private void scrollToElement(SelenideElement element) {
        element.shouldBe(Condition.visible);
        element.scrollTo();
    }

    private void ensureVisualEditor(SelenideElement visualTab) {
        if (visualTab.is(Condition.visible) && !"true".equals(visualTab.getAttribute("aria-pressed"))) {
            visualTab.click();
            visualTab.shouldHave(Condition.attribute("aria-pressed", "true"));
        }
    }

    public void submitIssue() {
        scrollToElement(submitButton);
        submitButton.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public boolean isIssueCreated() {
        return successMessage.shouldBe(Condition.visible).isDisplayed();
    }

    public String getCreatedIssueLink() {
        return createdIssueLink.shouldBe(Condition.visible).getAttribute("href");
    }

    public void setFixVersions() {
        scrollToElement(fixVersionsField);
        fixVersionsField.shouldBe(Condition.visible).click();
        fixVersionsFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setPriority() {
        scrollToElement(priorityField);
        priorityField.shouldBe(Condition.visible).click();
        priorityFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setLabels() {
        scrollToElement(labelsField);
        labelsField.shouldBe(Condition.visible).clear();
        labelsField.setValue("test-label");
        labelsField.shouldHave(Condition.value("test-label"));
    }

    public void setVersions() {
        scrollToElement(versionsField);
        versionsField.shouldBe(Condition.visible).click();
        versionsFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setAssignee() {
        scrollToElement(assigneeField);
        assigneeField.shouldBe(Condition.visible).click();
        assigneeFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setSeverity() {
        scrollToElement(severityField);
        severityField.shouldBe(Condition.visible).click();
        severitySecondOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setIssueLink() {
        scrollToElement(issueLinkTypeField);
        issueLinkTypeField.shouldBe(Condition.visible).click();
        issueLinkTypeFirstOption.shouldBe(Condition.visible, Condition.enabled).click();

        scrollToElement(issueLinkField);
        issueLinkField.shouldBe(Condition.visible).click();
        issueLinkField.setValue("test");
        issueLinkField.shouldHave(Condition.value("test"));
        issueLinkFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setEpicLink() {
        scrollToElement(epicLinkField);
        epicLinkField.shouldBe(Condition.visible).click();
        epicLinkFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void setSprint() {
        scrollToElement(sprintField);
        sprintField.shouldBe(Condition.visible).click();
        sprintFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public void createBug() {
        setSummary();
        setDescription();
        setFixVersions();
        setPriority();
        setLabels();
        setEnvironment();
        setVersions();
        setIssueLink();
        setEpicLink();
        setSprint();
        setAssignee();
        setSeverity();
        submitIssue();
    }
}
