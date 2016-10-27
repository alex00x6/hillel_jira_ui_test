package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Created by Storm on 27.10.2016.
 */
public class Dashboard {

    private WebDriver driver;

    public Dashboard(WebDriver driver){
        this.driver = driver;
    }

    public void openPage(){
        driver.get("http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa");
    }

    public void createClick(){driver.findElement(By.xpath("//*[@id=\"create_link\"]")).click();}

    public void createEnterProject(String project){
        driver.findElement(By.xpath("//*[@id=\"project-field\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"project-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"project-field\"]")).sendKeys(project, Keys.ENTER);
    }

    public void createEnterType(String issueType){
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).sendKeys(issueType, Keys.ENTER);
    }

    public void createEnterSummary(String summary){
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).sendKeys(summary, Keys.TAB);
    }

    public void createAssignToMeClick(){
        driver.findElement(By.xpath("//a[@id='assign-to-me-trigger']")).click();
    }

    public void createSubmitClick(){
        driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]")).click();
    }

    public String getKeyOfCreatedIssue(){
       String key =  driver.findElement(By.xpath("//*[@id=\"aui-flag-container\"]/div/div/a"))
                .getAttribute("data-issue-key");
        return key;
    }
}
