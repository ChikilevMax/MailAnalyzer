import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MailAnalysis {
    private WebDriver webDriver;
    private static final int NUMBER_OF_LETERS = 30;
    private List<String> senderName = new ArrayList<String>();
    private List<SenderCounter> senderCounters = new ArrayList<SenderCounter>();
    private int mailCounter = 0;


    public MailAnalysis(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private void loadMailProgram() {
        String pass;
        webDriver.get("https://mail.ukr.net/classic/login");
        WebElement login = findWithExplicitWait(15, By.id("login"));
        login.sendKeys("dom_prodakt@ukr.net");
        WebElement password = webDriver.findElement(By.id("password"));
        Scanner in = new Scanner(System.in);
        pass = in.nextLine();
        password.sendKeys(pass);
        WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"login-form\"]/div[3]/button"));
        loginButton.click();
    }

    private WebElement findWithExplicitWait(int waitSeconds, By by) {
        return (new WebDriverWait(webDriver, waitSeconds)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private void parseMailList() {
        while (mailCounter <= NUMBER_OF_LETERS) {
            List<WebElement> mailList = webDriver.findElements(By.xpath("//*[starts-with(@class, 'message unread')]"));
            mailCounter = mailCounter + mailList.size();
            parsingElementList(mailList);
            WebElement nextPageButton = webDriver.findElement(By.xpath("//*[@id=\"msglist-page\"]/div[4]/div/a[3]"));
            nextPageButton.click();
        }
    }

    private void parsingElementList(List<WebElement> webElement) {

        for (WebElement aWebElement : webElement) {
            addSenderCounter(aWebElement);
        }
    }

    private void addSenderCounter(WebElement currentWebElement) {
        int indexSenderName;
        String nameSender;
        String mailSubject;
        SenderCounter senderCounter;
        nameSender = currentWebElement.findElement(By.className("from-name")).getText();
        mailSubject = currentWebElement.findElement(By.className("subject-text")).getText();
        indexSenderName = senderName.indexOf(nameSender);
        if (indexSenderName == -1) {
            senderName.add(nameSender);
            senderCounter = new SenderCounter(nameSender);
            senderCounters.add(senderCounter);
            indexSenderName = senderName.indexOf(nameSender);
        }
        if (isVacancyMessage(mailSubject)) {
            senderCounters.get(indexSenderName).addJobListCount();
        } else {
            senderCounters.get(indexSenderName).addOtherListCount();
        }
    }

    private static boolean isVacancyMessage(String subject) {
        Boolean flag = false;
        if ((subject.contains("вакансия")) || (subject.contains("вакансии"))) {
            flag = true;
        }
        return flag;
    }

    public void performMailAnalysis() {
        SenderCounter currentSenderCounter;
        loadMailProgram();
        parseMailList();
        for (SenderCounter senderCounter : senderCounters) {
            currentSenderCounter = senderCounter;
            System.out.println("Отправитель - " + currentSenderCounter.getFrom());
            System.out.println("Писем о вакансиях - " + currentSenderCounter.getJobListCount());
            System.out.println("Других писем - " + currentSenderCounter.getOtherListCount());
            System.out.println("------------------------------------------------------------------");
        }
    }
}
