import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateBugPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By summaryField = By.xpath("//*[@id='summary']");
    private final By descriptionField = By.xpath("//*[@id='description']");
    private final By fixVersionsField = By.xpath("//*[@id='fixVersions']");
    private final By priorityField = By.xpath("//*[@id='priority-field']");
    private final By labelsField = By.xpath("//*[@id='labels-textarea']");
    private final By environmentField = By.xpath("//*[@id='environment']");
    private final By versionsField = By.xpath("//*[@id='versions']");
    private final By assigneeField = By.xpath("//*[@id='assignee-field']");
    private final By severityField = By.xpath("//*[@id='customfield_10400']");
    private final By descriptionVisualTab = By.xpath("//div[@field-id='description']//button[contains(text(), 'Визуальный')]");
    private final By environmentVisualTab = By.xpath("//div[@field-id='environment']//button[contains(text(), 'Визуальный')]");
    private final By submitButton = By.xpath("//*[@id='create-issue-submit']");
    private final By successMessage = By.xpath("//*[@id='aui-flag-container']//div[contains(@class, 'success')]");
    private final By createdIssueLink = By.xpath("//*[@id='aui-flag-container']//a[contains(@href, '/browse/')]");

    private final By issueLinkTypeField = By.xpath("//*[@id='issuelinks-linktype']");
    private final By issueLinkField = By.xpath("//*[@id='issuelinks-issues-textarea']");
    private final By epicLinkField = By.xpath("//*[@id='customfield_10100-field']");
    private final By sprintField = By.xpath("//*[@id='customfield_10104-field']");

    public CreateBugPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Заполнить название задачи
    public void setSummary() {
        WebElement summaryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField));
        summaryElement.clear();
        summaryElement.sendKeys("Тестовый баг");
    }

    // Заполнить описание задачи

    public void setDescription() {
        scrollToElement(descriptionField);
        ensureVisualEditor(descriptionVisualTab);

        // Заполняем через iframe визуального редактора
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@field-id='description']//iframe")));
        driver.switchTo().frame(iframe);
        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].innerHTML = arguments[1];", body, "Автотест создал этот баг");
        driver.switchTo().defaultContent();
    }

    // Заполнить поле "Окружение"

    public void setEnvironment() {
        // Прокрутить страницу до поля "Окружение"
        scrollToElement(environmentField);

        // Проверить и переключить на визуальный редактор
        ensureVisualEditor(environmentVisualTab);

        // Заполнить через iframe визуального редактора
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@field-id='environment']//iframe")));
        driver.switchTo().frame(iframe);
        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].innerHTML = arguments[1];", body, "Тестовое окружение");
        driver.switchTo().defaultContent();
    }

    private void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    // Убедиться, что выбран визуальный редактор для поля

    private void ensureVisualEditor(By visualTabLocator) {
        WebElement visualTabElement = driver.findElement(visualTabLocator);
        if (visualTabElement.isDisplayed() && !visualTabElement.getAttribute("aria-pressed").equals("true")) {
            visualTabElement.click();
            wait.until(ExpectedConditions.attributeToBe(visualTabElement, "aria-pressed", "true"));
        }
    }

    // Нажать кнопку создания задачи

    public void submitIssue() {
        scrollToElement(submitButton);

        WebElement submitButtonElement = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButtonElement.click();
    }

    // Проверить, что задача успешно создана

    public boolean isIssueCreated() {
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return successMessageElement.isDisplayed();
    }

    // Получить ссылку на созданную задачу

    public String getCreatedIssueLink() {
        WebElement linkElement = wait.until(ExpectedConditions.visibilityOfElementLocated(createdIssueLink));
        return linkElement.getAttribute("href");
    }

    // Установить версию для исправления

    public void setFixVersions() {
        scrollToElement(fixVersionsField);
        WebElement fixVersionsElement = wait.until(ExpectedConditions.presenceOfElementLocated(fixVersionsField));
        fixVersionsElement.click();

        // Выбираем первую опцию из списка
        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='fixVersions']//option[not(@value='-1')][1]")));
        firstOption.click();
    }

    // Установить приоритет (первое значение из списка)

    public void setPriority() {
        scrollToElement(priorityField);
        WebElement priorityElement = wait.until(ExpectedConditions.presenceOfElementLocated(priorityField));
        priorityElement.click();

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='priority-suggestions']//li[1]")));
        firstOption.click();
    }

    // Установить метки

    public void setLabels() {
        scrollToElement(labelsField);
        WebElement labelsElement = wait.until(ExpectedConditions.presenceOfElementLocated(labelsField));
        labelsElement.clear();
        labelsElement.sendKeys("test-label");
    }

    // Установить затронутые версии (первое значение из списка)

    public void setVersions() {
        scrollToElement(versionsField);
        WebElement versionsElement = wait.until(ExpectedConditions.presenceOfElementLocated(versionsField));
        versionsElement.click();

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='versions']//option[not(@value='-1')][1]")));
        firstOption.click();
    }

    // Установить исполнителя (первое значение из списка)

    public void setAssignee() {
        scrollToElement(assigneeField);
        WebElement assigneeElement = wait.until(ExpectedConditions.presenceOfElementLocated(assigneeField));
        assigneeElement.click();

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='assignee-suggestions']//li[1]")));
        firstOption.click();
    }

    // Установить Severity

    public void setSeverity() {
        scrollToElement(severityField);
        Select severitySelect = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(severityField)));
        severitySelect.selectByIndex(1); // Выбираем значение с индексом 1, т.к. 0 это "Не заполнено"
    }

    // Установить связанные задачи (первое значение из списка)

    public void setIssueLink() {
        scrollToElement(issueLinkTypeField);
        Select linkTypeSelect = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(issueLinkTypeField)));
        linkTypeSelect.selectByIndex(0);

        scrollToElement(issueLinkField);
        WebElement issueLinkElement = wait.until(ExpectedConditions.presenceOfElementLocated(issueLinkField));
        issueLinkElement.click();

        issueLinkElement.sendKeys("test");

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='issuelinks-issues-suggestions']//li[1]")));
        firstOption.click();
    }

    // Установить ссылку на эпик (первое значение из списка "Предложения")

    public void setEpicLink() {
        scrollToElement(epicLinkField);
        WebElement epicLinkElement = wait.until(ExpectedConditions.presenceOfElementLocated(epicLinkField));
        epicLinkElement.click();

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='customfield_10100-suggestions']//h5[text()='Предложения']/following-sibling::ul//li[1]")));
        firstOption.click();
    }

    // Установить спринт (первое значение из списка "Предложения")

    public void setSprint() {
        scrollToElement(sprintField);
        WebElement sprintElement = wait.until(ExpectedConditions.presenceOfElementLocated(sprintField));
        sprintElement.click();

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='customfield_10104-suggestions']//h5[text()='Предложения']/following-sibling::ul//li[1]")));
        firstOption.click();
    }

    // Создать баг с заполнением всех полей

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
