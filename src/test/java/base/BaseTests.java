package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.BookPage;

public class BaseTests {

    private WebDriver driver;
    protected BookPage bookPage;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://vs-prod.airtrfx.com/en-us/flights-from-orlando-to-manchester");
        //driver.get("http://localhost:18080/klaros-web/pages/login.seam");

        bookPage = new BookPage(driver);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}