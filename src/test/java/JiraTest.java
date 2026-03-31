import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


public class JiraTest extends BaseTest {

    @DisplayName("Test1: Авторизация пользователя в системе edujira.ifellow.ru")
    @Test
    void testAuthentication() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("AT5", "Qwerty123");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.toLowerCase().contains("dashboard"),
                "Авторизация не удалась: URL не содержит 'dashboard'. Текущий URL: " + currentUrl);
    }

    @DisplayName("Test2: Test1 + переход в проект TEST")
    @Test
    void testOpenTestProject() {
        testAuthentication();

        testProjectPage.openTestProject();

        Assertions.assertTrue(testProjectPage.isAtTestProject(),
                "Проект Test не открыт. Текущий URL: " + driver.getCurrentUrl());
    }

    @DisplayName("Test3: Test2 + создание задачи + проверка увеличения счетчика")
    @Test
    void testCheckIssuesCounter() {
        testOpenTestProject();

        // Проверить общее количество заведенных задач в проекте
        int initialIssuesCount = testProjectPage.getTotalIssuesCount();

        Assertions.assertTrue(initialIssuesCount > 0,
                "Количество задач должно быть положительным числом. Получено: " + initialIssuesCount);

        System.out.println("Количество задач до создания: " + initialIssuesCount);

        // Создать новую задачу
        testProjectPage.clickCreateIssue();

        CreateBugPage createBugPage = new CreateBugPage(driver);

        createBugPage.createBug();

        // Задача успешно создана
        Assertions.assertTrue(createBugPage.isIssueCreated(),
                "Задача не была создана");

        System.out.println("Задача успешно создана");

        // Вернуться в проект Test и проверить, что счетчик увеличился
        testProjectPage.openTestProject();
        Assertions.assertTrue(testProjectPage.isAtTestProject(),
                "Не удалось вернуться в проект Test");

        int finalIssuesCount = testProjectPage.getTotalIssuesCount();

        System.out.println("Количество задач после создания: " + finalIssuesCount);

        // Количество задач должно увеличиться на 1
        Assertions.assertEquals(initialIssuesCount + 1, finalIssuesCount,
                "Количество задач не увеличилось на 1 после создания задачи. " +
                        "Было: " + initialIssuesCount + ", Стало: " + finalIssuesCount);
    }

    @DisplayName("Test4: Test3 + проверка задачи TestSeleniumATHomework")
    @Test
    void testCheckTestSeleniumATHomework() {
        testCheckIssuesCounter();

        // Перейти в задачу TestSeleniumATHomework через поиск
        testProjectPage.openIssueByKey();

        IssuePage issuePage = new IssuePage(driver);
        Assertions.assertTrue(issuePage.isAtIssuePage(),
                "Не удалось открыть страницу задачи. Текущий URL: " + driver.getCurrentUrl());

        String actualStatus = issuePage.getIssueStatus();
        Assertions.assertEquals("СДЕЛАТЬ", actualStatus,
                "Статус задачи не соответствует ожидаемому. " +
                        "Ожидается: 'СДЕЛАТЬ', Получено: '" + actualStatus + "'");

        String actualFixVersions = issuePage.getFixVersions();
        Assertions.assertTrue(actualFixVersions.contains("Version 2.0"),
                "Версия в поле 'Исправить в версиях' не соответствует ожидаемой. " +
                        "Ожидается: 'Version 2.0', Получено: '" + actualFixVersions + "'");

        System.out.println("Проверка задачи TestSeleniumATHomework пройдена успешно:");
        System.out.println("  Статус: " + actualStatus);
        System.out.println("  Исправить в версиях: " + actualFixVersions);
    }

    @DisplayName("Test5: Весь сценарий с созданием бага")
    @Test
    void testCreateBug() throws InterruptedException {
        testCheckTestSeleniumATHomework();

        // Вернуться в проект Test и создать новый баг
        testProjectPage.openTestProject();
        Assertions.assertTrue(testProjectPage.isAtTestProject(),
                "Не удалось вернуться в проект Test");

        testProjectPage.clickCreateIssue();

        // Создание бага с описанием
        CreateBugPage createBugPage = new CreateBugPage(driver);

        createBugPage.createBug();

        Assertions.assertTrue(createBugPage.isIssueCreated(),
                "Баг не был создан успешно");

        System.out.println("Баг успешно создан:");

        // Перейти к созданной задаче и перевести в завершенный статус
        String createdIssueLink = createBugPage.getCreatedIssueLink();
        Assertions.assertNotNull(createdIssueLink, "Нет ссылки на созданную задачу");

        driver.get(createdIssueLink);

        // Мы на странице созданной задачи
        IssuePage createdIssuePage = new IssuePage(driver);
        Assertions.assertTrue(createdIssuePage.isAtIssuePage(),
                "Не открылась страница созданной задачи.");

        String initialStatus = createdIssuePage.getIssueStatus();
        System.out.println("Статус созданной задачи: " + initialStatus);

        // Перевод задачи в завершенный статус
        createdIssuePage.completeIssue();

        // Ждем обновления статуса
        TimeUnit.SECONDS.sleep(2);

        String finalStatus = createdIssuePage.getIssueStatus();
        System.out.println("Статус задачи после завершения: " + finalStatus);

        // Статус задачи должен быть завершенным
        Assertions.assertEquals("ГОТОВО", finalStatus,
                "Задача не завершена. Статус: " + finalStatus);
    }
}
