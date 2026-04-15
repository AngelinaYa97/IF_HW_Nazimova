package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenProjectSteps {

    private final TestContext context = TestContext.getInstance();

    @Когда("я открываю проект {string}")
    public void openProject(String projectName) {
        context.openProjectPage.openProject(projectName);
    }

    @Тогда("URL содержит {string}")
    public void verifyUrlContains(String expectedText) {
        String currentUrl = webdriver().driver().url();
        assertTrue(currentUrl.toLowerCase().contains(expectedText.toLowerCase()),
                "Я не вижу '" + expectedText + "' в URL. Текущий URL: " + currentUrl);
    }
}