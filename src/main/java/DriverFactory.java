import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private static WebDriver webDriver;

    public static WebDriver getDriver(){
        if (webDriver == null){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Boss\\Selenium\\chromedriver.exe");
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }
}
