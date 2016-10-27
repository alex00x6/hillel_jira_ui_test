package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 * Created by Storm on 25.10.2016.
 */
public class JiraTests {

    protected WebDriver driver;
    String summary = "Some summary for createIssue via Webdriver";
    String issueType = "Bug";
    String login = "alex00x6"; //"a.a.piluck2"
    String password = "000000"; // "1234"
    String created_issue = "";

    @BeforeTest
    public void beforeTest(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void loginSuccessful() {
        //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        //WebDriver driver = new ChromeDriver();

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

        //sleep(2000);

        String eTitle1 = "System Dashboard - JIRA";
        String aTitle1 = "";
        aTitle1 = driver.getTitle();
        assertEquals(aTitle1, eTitle1);

        //sleep(4000);

        // закрываем окно браузера
        //driver.close();
        //закрываем окно браузера и убиваем процесс драйвера
        //driver.quit();
    }

    @Test(dependsOnMethods = {"loginSuccessful"})
    public void createIssueSuccessful(){
        //WebDriver driver = new ChromeDriver();

        //открываем дашборд
        driver.get("http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa");
        //находим кнопку Create и нажимаем
        driver.findElement(By.xpath("//*[@id=\"create_link\"]")).click();

        //sleep(3000);

        //корявым способом меняем со Story на Bug
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).sendKeys(issueType, Keys.ENTER);

        //sleep(1500);

        //находим поле самери и пишем туда что-то
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).clear();
        sleep(500); //без этого слипа почему-то падает
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).sendKeys(summary);

        //sleep(1500);

        //нажимаем на элемент assign to me
        driver.findElement(By.xpath("//a[@id='assign-to-me-trigger']")).click();
        //нажимаем кнопку submit
        driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]")).click();


        created_issue = driver
                .findElement(By.xpath("//*[@id=\"aui-flag-container\"]/div/div/a"))
                .getAttribute("data-issue-key");
        System.out.println(created_issue);
    }

    @Test(dependsOnMethods = {"loginSuccessful", "createIssueSuccessful"})
    public void deleteCreatedIssue(){
        driver.get("http://soft.it-hillel.com.ua:8080/browse/"+created_issue);

        driver.findElement(By.xpath("//*[@id=\"opsbar-operations_more\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue-submit\"]")).submit();

    }

    @AfterTest
    public void afterTest(){
        //sleep(10000);
        //закрываем окно браузера и убиваем процесс драйвера
        //driver.quit();
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
