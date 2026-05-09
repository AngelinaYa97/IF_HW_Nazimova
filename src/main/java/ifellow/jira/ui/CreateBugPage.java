package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class CreateBugPage {

    private final SelenideElement issueStatus = $x("//*[@id='status-val']").as("Статус задачи");
    private final SelenideElement workflowButton = $x("//*[@id='opsbar-transitions_more']").as("Кнопка 'Бизнес-процесс'");
    private final SelenideElement workflowDropdown = $x("//*[@id='opsbar-transitions_more_drop']").as("Выпадающее меню бизнес-процесса");
    private final SelenideElement doneTransition = $x("//aui-item-link[@id='action_id_31']").as("Кнопка 'Выполнено'");
    private final SelenideElement createIssueButton = $x("//*[@id='create_link']").as("Кнопка создания задачи");
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

    public CreateBugPage() {}
    @Step("Нажать кнопку создания задачи")
    public void clickCreateIssue() {
        createIssueButton.click();
    }


    @Step("Заполнить поле 'Название'")
    public void setSummary() {
        summaryField.shouldBe(Condition.visible).clear();
        summaryField.setValue("Тестовый баг");
    }

    @Step("Заполнить поле 'Описание'")
    public void setDescription() {
        descriptionField.scrollTo();
        ensureVisualEditor(descriptionVisualTab);
        Selenide.switchTo().frame(descriptionIframe);
        body.shouldBe(Condition.visible).setValue("Автотест создал этот баг");
        Selenide.switchTo().defaultContent();
    }

    @Step("Заполнить поле 'Окружение'")
    public void setEnvironment() {
        environmentField.scrollTo();
        ensureVisualEditor(environmentVisualTab);
        Selenide.switchTo().frame(environmentIframe);
        body.shouldBe(Condition.visible).setValue("Тестовое окружение");
        Selenide.switchTo().defaultContent();
    }

    @Step("Выбрать 'Исправить в версиях'")
    public void setFixVersions() {
        fixVersionsField.scrollTo();
        fixVersionsField.click();
        fixVersionsFirstOption.shouldBe(Condition.visible, Condition.enabled).click();
    }

    @Step("Выбрать приоритет")
    public void setPriority() {
        priorityField.scrollTo();
        priorityField.click();
        priorityFirstOption.click();
    }

    @Step("Заполнить метки")
    public void setLabels() {
        labelsField.scrollTo();
        labelsField.clear();
        labelsField.setValue("test-label");
    }

    @Step("Выбрать затронутые версии")
    public void setVersions() {
        versionsField.scrollTo();
        versionsField.click();
        versionsFirstOption.click();
    }

    @Step("Выбрать исполнителя")
    public void setAssignee() {
        assigneeField.scrollTo();
        assigneeField.click();
        assigneeFirstOption.click();
    }

    @Step("Выбрать Severity")
    public void setSeverity() {
        severityField.scrollTo();
        severityField.click();
        severitySecondOption.click();
    }

    @Step("Установить связанную задачу")
    public void setIssueLink() {
        issueLinkTypeField.scrollTo();
        issueLinkTypeField.click();
        issueLinkTypeFirstOption.click();
        issueLinkField.scrollTo();
        issueLinkField.click();
        issueLinkField.setValue("test");
        issueLinkFirstOption.click();
    }

    @Step("Установить ссылку на эпик")
    public void setEpicLink() {
        epicLinkField.scrollTo();
        epicLinkField.click();
        epicLinkFirstOption.click();
    }

    @Step("Установить спринт")
    public void setSprint() {
        sprintField.scrollTo();
        sprintField.click();
        sprintFirstOption.click();
    }

    @Step("Нажать кнопку 'Создать'")
    public void submitIssue() {
        submitButton.scrollTo();
        submitButton.click();
    }

    @Step("Проверить, что задача создана")
    public void verifyIssueCreated() {
        successMessage.shouldBe(Condition.visible, Condition.enabled);
    }

    @Step("Получить ссылку на созданную задачу")
    public String getCreatedIssueLink() {
        return createdIssueLink.shouldBe(Condition.visible).getAttribute("href");
    }

    @Step("Перевести задачу в статус 'Выполнено'")
    public void completeIssue() {
        workflowButton.shouldBe(Condition.visible, Condition.enabled).click();
        workflowDropdown.shouldBe(Condition.visible, Duration.ofSeconds(10));
        doneTransition.shouldBe(Condition.visible, Condition.enabled).click();
    }

    @Step("Проверить, что статус задачи изменился на '{expectedStatus}'")
    public void waitForStatusChange(String expectedStatus, Duration timeout) {
        issueStatus.shouldHave(Condition.text(expectedStatus), timeout);
    }

    @Step("Создать баг (заполнить все поля и отправить)")
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
        verifyIssueCreated();
    }

    private void ensureVisualEditor(SelenideElement visualTab) {
        if (visualTab.is(Condition.visible) && !"true".equals(visualTab.getAttribute("aria-pressed"))) {
            visualTab.click();
            visualTab.shouldHave(Condition.attribute("aria-pressed", "true"));
        }
    }
}