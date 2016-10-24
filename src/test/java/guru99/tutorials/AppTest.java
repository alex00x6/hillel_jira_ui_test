package guru99.tutorials;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AppTest {
	
	protected WebDriver driver;

    @Test
    public void simpleTest() {
		WebDriver driver = new ChromeDriver();
       
        String eTitle = "Meet Guru99";
        String aTitle = "";
 
        // запустить firefox и перейти по адресу
        driver.get("http://www.guru99.com/");
        // разворачивает окно браузера
        driver.manage().window().maximize();
        // получить значение у тайтла страницы
        aTitle = driver.getTitle();

        // выполняем проверку
        assertEquals(aTitle, eTitle);

        // закрываем окно браузера
        driver.close();
	}
}

