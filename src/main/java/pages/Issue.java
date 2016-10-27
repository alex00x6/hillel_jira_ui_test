package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Created by Storm on 27.10.2016.
 */
public class Issue {

    private WebDriver driver;

    public Issue(WebDriver driver){
        this.driver = driver;
    }

    public void openPage(String key){
        driver.get("http://soft.it-hillel.com.ua:8080/browse/"+key);
    }

    public void addComment(String comment_text){
        driver.findElement(By.xpath("//*[@id=\"comment-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"comment\"]")).sendKeys(comment_text);
        driver.findElement(By.xpath("//*[@id=\"issue-comment-add-submit\"]")).submit();
    }

    public void changeType(String issueType){
        driver.findElement(By.xpath("//*[@id=\"type-val\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).sendKeys(issueType, Keys.ALT+"S");
    }

    public void changeType2(){ //пока лежит тут, не используется, ибо не пашет.
        driver.findElement(By.xpath("//*[@id=\"type-val\"]")).click();
        driver.findElement(By.xpath("//span[@id='type-val']")).clear();
        //driver.findElement(By.xpath("//span[@id='type-val']")).click();
        driver.findElement(By.xpath("//div[@id='issuetype-single-select']")).click();
        driver.findElement(By.xpath("//li[@id='epic-3']/a")).click();//тут проблема, у id окончание постоянно меняется, не знаю как прописать id='epic-*'
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
    }

    public void deleteIssue(){
        driver.findElement(By.xpath("//*[@id=\"opsbar-operations_more\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue-submit\"]")).submit();
    }

    public void changeReporter(String reporter){
        driver.findElement(By.xpath(".//*[@id='reporter-val']")).click();
        driver.findElement(By.xpath(".//*[@id='reporter-field']")).sendKeys(reporter, Keys.ENTER);
    }

    public void changePriority(String priority){
        driver.findElement(By.xpath(".//*[@id='priority-val']")).click();
        driver.findElement(By.xpath("//*[@id=\"priority-field\"]")).sendKeys(priority, Keys.ALT+"S");
    }

    public void changeSummary(String summary){
        driver.findElement(By.xpath(".//*[@id='summary-val']")).click();
        driver.findElement(By.xpath(".//*[@id='summary']")).sendKeys(summary, Keys.ALT+"S");
    }
}
