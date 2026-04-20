package steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;

public class OpenProjectSteps {

    private final TestContext context = TestContext.getInstance();

    @Когда("я открываю проект {string}")
    public void openProject(String projectName) {
        context.openProjectPage.openProject(projectName);
    }

    @Тогда("URL содержит {string}")
    public void verifyUrlContains(String expectedText) {
        String currentUrl = Selenide.webdriver().driver().url();
        Assertions.assertTrue(currentUrl.toLowerCase().contains(expectedText.toLowerCase()),
                "Я не вижу '" + expectedText + "' в URL. Текущий URL: " + currentUrl);
    }
}