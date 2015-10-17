import org.openqa.selenium.WebDriver;

public class SeleniumTest {
    private static WebDriver webDriver = DriverFactory.getDriver();


    public static void main(String args[]) {
        MailAnalysis mailAnalysis = new MailAnalysis(webDriver);
        mailAnalysis.performMailAnalysis();
        webDriver.close();

    }
}
