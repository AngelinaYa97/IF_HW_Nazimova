package steps;

import ifellow.jira.ui.*;

public class TestContext {
    private static TestContext instance;

    public final LoginPage loginPage;
    public final TestProjectPage testProjectPage;
    public final OpenProjectPage openProjectPage;
    public final CreateBugPage createBugPage;
    public final IssuePage issuePage;

    private TestContext() {
        loginPage = new LoginPage();
        testProjectPage = new TestProjectPage();
        openProjectPage = new OpenProjectPage();
        createBugPage = new CreateBugPage();
        issuePage = new IssuePage();
    }

    public static TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }
}