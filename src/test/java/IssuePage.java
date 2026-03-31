import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IssuePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By issueStatus = By.xpath("//*[@id='status-val']");
    private final By fixVersionsField = By.xpath("//*[@id='fixVersions-field']");
    private final By workflowButton = By.xpath("//*[@id='opsbar-transitions_more']");

    public IssuePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Получить статус задачи

    public String getIssueStatus() {
        WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(issueStatus));
        return statusElement.getText().trim();
    }

    // Получить значение поля "Исправить в версиях"

    public String getFixVersions() {
        WebElement fixVersionsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fixVersionsField));
        return fixVersionsElement.getText().trim();
    }

    // Проверить, что мы на странице задачи

    public boolean isAtIssuePage() {
        return driver.getCurrentUrl().contains("/browse/");
    }

    // Перевод задачи в статус "Готово"

    public void completeIssue() throws InterruptedException {
        // Нажать на кнопку "Бизнес-процесс"
        WebElement workflowBtn = wait.until(ExpectedConditions.elementToBeClickable(workflowButton));
        workflowBtn.click();

        // Ожидание появления выпадающего списка
        Thread.sleep(1000);

        // Выбрать "Выполнено" из выпадающего списка
        WebElement doneTransition = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//aui-item-link[@id='action_id_31']")));
        doneTransition.click();
    }
}
