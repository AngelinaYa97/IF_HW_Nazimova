package ifellow.jira.ui;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class OpenProjectPage {
    private final SelenideElement projectMenu = $x("//*[@id='browse_link']")
            .as("Drop-down 'Проекты'");
    private final SelenideElement testProject = $x("//*[@id='admin_main_proj_link_lnk']")
            .as("Проект Test");

    public void openProject(String textProject) {
        projectMenu.shouldBe(Condition.visible, Condition.enabled).click(ClickOptions.usingJavaScript());
        testProject.shouldBe(Condition.visible, Condition.enabled).click();
    }
}
