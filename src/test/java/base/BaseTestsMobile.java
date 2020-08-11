package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.BookPage;

import java.util.HashMap;
import java.util.Map;

public class BaseTestsMobile {

    private WebDriver driver;
    protected BookPage bookPage;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        Map<String,String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");
        Map<String, Object> chromeOptions = new HashMap<>();
        chromeOptions.put("mobileEmulation",mobileEmulation);
        capabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        driver.get("https://vs-prod.airtrfx.com/en-us/flights-from-orlando-to-manchester");
//        //driver.get("http://localhost:18080/klaros-web/pages/login.seam");
        bookPage = new BookPage(driver);
    }

    @AfterMethod
    public void tearDown(){
        //driver.quit();
    }

}
