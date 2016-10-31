package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Dashboard;
import pages.Issue;
import pages.LoginPage;
import utils.Helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Storm on 25.10.2016.
 */
public class JiraTestsGRID {

    protected WebDriver driver;
    Helpers helpers = new Helpers();
    String summary = "Some summary for createIssue via WebDriver";
    String issueType = "Bug";
    String issueTypeNew = "Epic";
    String login = "alex00x6"; //"a.a.piluck2"
    String password = "666999"; // "1234"
    String created_issue = "";
    String comment_text = "Some comment for addCommentToIssue via WebDriver+Grid";
    String currentDate = "";
    String reporter = "a.a.piluck";
    String priority = "High";
    String summary_new = "Updated summary, blah blah blah";

    @BeforeTest
    public void beforeTest(){
        configForChrome();
        //configForGrid();

        // разворачивает окно браузера
        driver.manage().window().maximize();
    }


    @Test//(groups = {"LoginCreate"})
    public void loginSuccessful() {
        LoginPage loginPage = new LoginPage(driver);

        String eTitle1 = "System Dashboard - JIRA";
        String eTitle = "Log in - JIRA";

        //открываем страницу логина. ну в целом логично
        loginPage.openPage();

        // получить значение у тайтла страницы
        String aTitle = driver.getTitle();
        // выполняем проверку, попали ли мы на страницу с нужным тайтлом
        assertEquals(aTitle, eTitle);

        loginPage.enterLogin(login);
        loginPage.enterPassword(password);
        loginPage.clickSubmit();

        String aTitle1 = driver.getTitle();

        assertEquals(aTitle1, eTitle1);

        helpers.makeScreenshot("loginSuccessful", driver, currentDate);
    }

    @Test( dependsOnMethods = {"loginSuccessful"})
    public void createIssueSuccessful(){

        Dashboard dashboard = new Dashboard(driver);

        //открываем дашборд
        dashboard.openPage();
        //находим кнопку Create и нажимаем
        dashboard.createClick();

        //делаем так, чтоб issue точно создалась в проекте QAAUT xD
        dashboard.createEnterProject("QAAUT");

        //ожидаем пока элемент станет visible
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"issuetype-field\"]")));

        helpers.sleep(900);

        //корявым способом меняем со Story на Bug
        dashboard.createEnterType(issueType);

        //находим поле самери и пишем туда что-то
        helpers.sleep(500);
        dashboard.createEnterSummary(summary);

        //нажимаем на элемент assign to me
        dashboard.createAssignToMeClick();
        //нажимаем кнопку submit
        dashboard.createSubmitClick();

        created_issue = dashboard.getKeyOfCreatedIssue();
        System.out.println(created_issue);

        Assert.assertNotNull(created_issue);
        assertTrue(created_issue.contains("QAAUT-"));

        helpers.makeScreenshot("createIssueSuccessful", driver, currentDate);
    }

    @Test(dependsOnMethods = {"createIssueSuccessful"})
    public void addCommentToIssue(){
        Issue issue = new Issue(driver);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //добавляем коммент
        issue.addComment(comment_text);
        //делаем скриншотец
        helpers.makeScreenshot("addCommentToIssue", driver, currentDate);
    }

    @Test(dependsOnMethods = {"addCommentToIssue"})
    public void changeTypeOfIssue(){
        Issue issue = new Issue(driver);

        WebDriverWait wait = new WebDriverWait(driver, 3000);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"type-val\"]")));
        //меняем Type of Issue
        issue.changeType(issueTypeNew);
        //делаем скриншотец
        helpers.makeScreenshot("changeTypeOfIssue", driver, currentDate);
    }

    @Test(dependsOnMethods = {"changeTypeOfIssue"})
    public void changeReporter(){
        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем репортЁра :)
        issue.changeReporter(reporter);
        //делаем скриншотец
        helpers.makeScreenshot("changeReporter", driver, currentDate);
    }

    @Test(dependsOnMethods = {"changeReporter"})
    public void changePriority(){
        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем приоритет
        issue.changePriority(priority);
        //делаем скриншотец
        helpers.makeScreenshot("changePriority", driver, currentDate);
    }

    @Test(dependsOnMethods = {"changePriority"})
    public void changeSummary(){
        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем summary
        issue.changeSummary(summary_new);
        //делаем скриншотец
        helpers.makeScreenshot("changeSummary", driver, currentDate);
    }


    //@Test(dependsOnMethods = {"changeSummary"})
    public void deleteCreatedIssue(){
        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //открываем More, нажимаем удалить, подтверждаем
        issue.deleteIssue();
        //делаем скриншотец
        helpers.makeScreenshot("deleteCreatedIssue", driver, currentDate);
    }

    @AfterTest
    public void afterTest(){
        //удаляем чего мы там насоздавали(вынес сюда вызов метода, ибо на мой взгляд так логичнее)
        deleteCreatedIssue();
        //ждем, чтоб было время посмотреть, а потом:
        //закрываем окно браузера и убиваем процесс драйвера
        //в случае с гридом убивает созданную сессию(и слава богу и так и надо)
        helpers.sleep(5000);
        driver.quit();
    }


    public void configForGrid(){
        currentDate = helpers.getTime();

        URL hostURL = null;
        try {
            hostURL = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.LINUX);

        driver = new RemoteWebDriver(hostURL, capability);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    public void configForChrome(){
        currentDate = helpers.getTime();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }



}
