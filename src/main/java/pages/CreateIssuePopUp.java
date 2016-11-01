package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Created by Storm on 01.11.2016.
 */
public class CreateIssuePopUp {
    private WebDriver driver;

    public CreateIssuePopUp(WebDriver driver){
        this.driver = driver;
    }

    public void enterProject(String project){
        driver.findElement(By.xpath("//*[@id=\"project-field\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"project-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"project-field\"]")).sendKeys(project, Keys.ENTER);
    }

    public void enterType(String issueType){
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).sendKeys(issueType, Keys.ENTER);
    }

    public void enterSummary(String summary){
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"summary\"]")).sendKeys(summary, Keys.TAB);
    }

    public void clickAssignToMe(){
        driver.findElement(By.xpath("//a[@id='assign-to-me-trigger']")).click();
    }

    public void clickSubmit(){
        driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]")).click();
    }

    public String getKeyOfCreatedIssue(){
        String key =  driver.findElement(By.xpath("//*[@id=\"aui-flag-container\"]/div/div/a"))
                .getAttribute("data-issue-key");
        return key;
    }

}
