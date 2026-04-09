package ifellow.jira.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;

import static com.codeborne.selenide.Selenide.$x;

public class TestProjectPage {

    private final SelenideElement projectsMenu = $x("//*[@id='browse_link']")
            .as("Меню 'Проекты'");
    private final SelenideElement testProject = $x("//*[@id='admin_main_proj_link_lnk']")
            .as("Проект Test");
    private final SelenideElement createIssueButton = $x("//*[@id='create_link']")
            .as("Кнопка создания задачи");
    private final SelenideElement issueCounter = $x("//div[@class='showing']/span")
            .as("Счетчик задач");
    private final SelenideElement searchInput = $x("//*[@id='quickSearchInput']")
            .as("Поле поиска");
    private final SelenideElement searchResultItem = $x("//div[contains(@class, 'quicksearch-dropdown')]//span[contains(@class, 'quick-search-item-title') and text()='TestSeleniumATHomework']/ancestor::a")
            .as("Результат поиска задачи");

    public void openTestProject() {
        projectsMenu.shouldBe(Condition.visible, Condition.enabled).click();
        testProject.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public boolean isAtTestProject() {
        return Selenide.webdriver().driver().url().contains("TEST");
    }

    public int getTotalIssuesCount() {
        String counterText = issueCounter.shouldBe(Condition.visible).getText();
        String[] parts = counterText.split("из");
        return Integer.parseInt(parts[1].trim());
    }

    public void openIssueByKey() {
        searchInput.shouldBe(Condition.visible).clear();
        searchInput.setValue("TestSeleniumATHomework");
        searchInput.shouldHave(Condition.value("TestSeleniumATHomework"));
        searchResultItem.shouldBe(Condition.visible, Condition.enabled).click();
        Selenide.webdriver().shouldHave(WebDriverConditions.urlContaining("/browse/"));
    }

    public void clickCreateIssue() {
        createIssueButton.shouldBe(Condition.visible, Condition.enabled).click();
    }
}
