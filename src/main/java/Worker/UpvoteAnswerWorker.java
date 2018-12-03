package Worker;

import Objects.AccountDriver;
import Objects.QuestionsUtil;
import Objects.configFile.Account;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page.Zapytaj.LoginPage;
import page.Zapytaj.MainPage;
import page.Zapytaj.QuestionPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UpvoteAnswerWorker {

    private static final Logger LOG = LoggerFactory.getLogger(UpvoteAnswerWorker.class);
    private static final String URL = "https://zapytaj.onet.pl/";

    private List<WebDriver> drivers;
    private String accountNameAnswer;
    private List<Account> accounts;
    private List<AccountDriver> accountDrivers;

    public UpvoteAnswerWorker(String mainAccountName, List<Account> accounts) {
        this.accountNameAnswer = mainAccountName;
        this.accounts = accounts;
    }

    private WebDriver configureDriver(String userName) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        String userDir = System.getProperty("user.dir");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-data-dir=" + userDir + "/bin/" + userName);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    public void start() throws Exception {
        accountDrivers = createAccountDrivers();
        loginToAllDrivers();

        while (true) {
            String questionToUpvote = QuestionsUtil.getNewAnswerToUpvote();
            if (questionToUpvote != null) {
                upvoteAnswer(questionToUpvote);
            } else {
                Thread.sleep(5000);
            }
        }
    }

    public List<AccountDriver> createAccountDrivers() {
        List<AccountDriver> accountDrivers = new ArrayList<>();
        for (Account account : accounts) {
            WebDriver driver = configureDriver(account.getEmail());
            AccountDriver accountDriver = new AccountDriver(driver, account, driver.getWindowHandle());
            accountDrivers.add(accountDriver);
        }
        return accountDrivers;
    }

    public void loginToAllDrivers() throws Exception {
        accountDrivers.parallelStream().forEach(accountDriver -> {
            try {
                WebDriver driver = accountDriver.getDriver();
                Account account = accountDriver.getAccount();
                driver.get(URL);
                MainPage mainPage = new MainPage(driver);
                mainPage.dismissPopupIfVisible();
                if (mainPage.isLoginPossible()) {
                    LoginPage loginPage = mainPage.clickLoginButton();
                    mainPage.dismissPopupIfVisible();
                    loginPage.login(account.getEmail(), account.getPassword());
                }
            } catch (Exception e) {
                LOG.error("Could not login to " + accountDriver.getAccount().getEmail());
            }
        });
    }

    public QuestionPage openQuestionPage(WebDriver driver, String url) throws Exception {
        driver.get(url);
        return switchToWindow(driver, url);
    }

    public void upvoteAnswer(String link) throws Exception {
        accountDrivers.parallelStream().forEach(accountDriver -> {
            try {
                WebDriver driver = accountDriver.getDriver();
                QuestionPage questionPage = openQuestionPage(driver, link);
                questionPage.upvoteAnswer(accountNameAnswer);
            } catch (Exception e) {
            }
        });
    }

    private QuestionPage switchToWindow(WebDriver driver, String linkToMatch) throws Exception {
        driverWait(driver, 1);
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getCurrentUrl().contains(linkToMatch)) {
                return new QuestionPage(driver);
            }
        }
        throw new Exception("New window was not found.");
    }


    private void driverWait(WebDriver driver, int seconds) throws InterruptedException {
        synchronized (driver) {
            driver.wait(seconds * 1000);
        }
    }
}
