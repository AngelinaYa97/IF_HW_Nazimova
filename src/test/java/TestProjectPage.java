import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestProjectPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By projectsMenu = By.xpath("//*[@id='browse_link']");
    private final By testProject = By.xpath("//*[@id='admin_main_proj_link_lnk']");
    private final By createIssueButton = By.xpath("//*[@id='create_link']");
    private final By issueCounter = By.xpath("//div[@class='showing']/span");
    private final By searchInput = By.xpath("//*[@id='quickSearchInput']");

    public TestProjectPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Открыть проект Test из меню Projects

    public void openTestProject() {
        wait.until(ExpectedConditions.elementToBeClickable(projectsMenu)).click();

        wait.until(ExpectedConditions.elementToBeClickable(testProject)).click();
    }

    // Проверить, что мы на странице проекта Test

    public boolean isAtTestProject() {
        return driver.getCurrentUrl().contains("TEST");
    }

    // Получить текущее количество задач в проекте
    // Парсит строку вида "1 из 30" и возвращает общее количество

    public int getTotalIssuesCount() {
        WebElement counterElement = wait.until(ExpectedConditions.visibilityOfElementLocated(issueCounter));
        String counterText = counterElement.getText();

        String[] parts = counterText.split("из");
        return Integer.parseInt(parts[1].trim());
    }

    // Открыть задачу по её имени через поиск

    public void openIssueByKey() {
        // Ввести название задачи в поле поиска
        WebElement searchInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchInputElement.clear();
        searchInputElement.sendKeys("TestSeleniumATHomework");

        // Дождаться появления выпадающего списка с результатами поиска
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Найти элемент с нужным именем задачи в выпадающем списке
        WebElement resultItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'quicksearch-dropdown')]//span[contains(@class, 'quick-search-item-title') and text()='TestSeleniumATHomework']/ancestor::a")));
        resultItem.click();

        wait.until(ExpectedConditions.urlContains("/browse/"));
    }

    // Нажать кнопку создания новой задачи

    public void clickCreateIssue() {
        WebElement createIssueButtonElement = wait.until(ExpectedConditions.elementToBeClickable(createIssueButton));
        createIssueButtonElement.click();
    }
}
