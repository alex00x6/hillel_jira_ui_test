package Jira;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 * Created by Storm on 25.10.2016.
 */
public class JiraTestsGRID {

    protected WebDriver driver;
    String summary = "Some summary for createIssue via WebDriver";
    String issueType = "Bug";
    String login = "alex00x6"; //"a.a.piluck2"
    String password = "652113"; // "1234"
    String created_issue = "";
    String comment_text = "Some comment for addCommentToIssue via WebDriver+Grid";
    String currentDate = "";

    @BeforeTest
    public void beforeTest(){

        currentDate = getTime();

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
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void loginSuccessful() {
        String eTitle = "Log in - JIRA";
        String aTitle = "";

        // запустить firefox и перейти по адресу
        driver.get("http://soft.it-hillel.com.ua:8080/login.jsp");
        // разворачивает окно браузера
        driver.manage().window().maximize();
        // получить значение у тайтла страницы
        aTitle = driver.getTitle();
        // выполняем проверку
        assertEquals(aTitle, eTitle);

        driver.findElement(By.xpath("//input[@id='login-form-username']")).sendKeys(login);
        driver.findElement(By.xpath("//input[@id='login-form-password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@id='login-form-submit']")).click();

        String eTitle1 = "System Dashboard - JIRA";
        String aTitle1 = "";
        aTitle1 = driver.getTitle();
        assertEquals(aTitle1, eTitle1);

        makeScreenshot("loginSuccessful");
    }

    @Test(dependsOnMethods = {"loginSuccessful"})
    public void createIssueSuccessful(){

        //открываем дашборд
        driver.get("http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa");
        //находим кнопку Create и нажимаем
        driver.findElement(By.xpath("//*[@id=\"create_link\"]")).click();

        //корявым способом меняем со Story на Bug
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).sendKeys(issueType, Keys.ENTER);

        //находим поле самери и пишем туда что-то
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).clear();
        sleep(500); //без этого слипа почему-то падает
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).sendKeys(summary);

        //нажимаем на элемент assign to me
        driver.findElement(By.xpath("//a[@id='assign-to-me-trigger']")).click();
        //нажимаем кнопку submit
        driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]")).click();

        created_issue = driver
                .findElement(By.xpath("//*[@id=\"aui-flag-container\"]/div/div/a"))
                .getAttribute("data-issue-key");
        System.out.println(created_issue);

        makeScreenshot("createIssueSuccessful");
    }

    @Test(dependsOnMethods = {"createIssueSuccessful"})
    public void addCommentToIssue(){
        driver.get("http://soft.it-hillel.com.ua:8080/browse/"+created_issue);

        driver.findElement(By.xpath("//*[@id=\"comment-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"comment\"]")).sendKeys(comment_text);
        driver.findElement(By.xpath("//*[@id=\"issue-comment-add-submit\"]")).submit();

        makeScreenshot("addCommentToIssue");
    }

    @Test(dependsOnMethods = {"addCommentToIssue"})
    public void changeTypeOfIssue(){
        driver.get("http://soft.it-hillel.com.ua:8080/browse/"+created_issue);

        driver.findElement(By.xpath("//span[@id='type-val']")).click();
        driver.findElement(By.xpath("//div[@id='issuetype-single-select']")).click();
        driver.findElement(By.xpath("//li[@id='epic-3']/a")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

        makeScreenshot("changeTypeOfIssue");
    }

    @Test(dependsOnMethods = {"changeTypeOfIssue"})
    public void deleteCreatedIssue(){
        driver.get("http://soft.it-hillel.com.ua:8080/browse/"+created_issue);

        driver.findElement(By.xpath("//*[@id=\"opsbar-operations_more\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue-submit\"]")).submit();

        makeScreenshot("deleteCreatedIssue");
    }

    @AfterTest
    public void afterTest(){
        //закрываем окно браузера и убиваем процесс драйвера
        //в случае с гридом убивает созданную сессию(и слава богу и так и надо)
        driver.quit();
    }

    public void makeScreenshot(){
        sleep(1000);
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Random rn = new Random();
        int randInt = rn.nextInt();
        try {
            FileUtils.copyFile(screen, new File("C://Users/Storm/Desktop/scr/scr"+randInt+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeScreenshot(String name){
        sleep(1000);
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File("C://Users/Storm/Desktop/scr/"+currentDate+"/"+name+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss (dd.MM.yyyy)");
        Date date = new Date();
        String now = dateFormat.format(date);
        return now;
    }

    public void sleep(int time){
        //Ожидаем
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
