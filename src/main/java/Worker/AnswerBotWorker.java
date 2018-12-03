package Worker;

import Objects.configFile.MainAccount;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page.Zapytaj.LoginPage;
import page.Zapytaj.MainPage;
import page.Zapytaj.QuestionsPage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnswerBotWorker {
    private static Logger LOG = LoggerFactory.getLogger(AnswerBotWorker.class);

    private static final String URL = "https://zapytaj.onet.pl/";

    private WebDriver driver;
    private List<String> answerMessage;
    private String answerFileName;
    private MainAccount mainAccount;
    private String category;

    public AnswerBotWorker(String answerFileName, MainAccount mainAccount, String category) {
        this.answerFileName = answerFileName;
        this.mainAccount = mainAccount;
        this.category = category;
    }

    private WebDriver configureDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        String userDir = System.getProperty("user.dir");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-data-dir=" + userDir + "/bin/profile");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    public synchronized void start() throws Exception {
        answerMessage = getTextFromFile(answerFileName);

        driver = configureDriver();

        driver.get(URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.dismissPopupIfVisible();
        if (mainPage.isLoginPossible()) {
            LoginPage loginPage = mainPage.clickLoginButton();
            mainPage.dismissPopupIfVisible();
            loginPage.login(mainAccount.getEmail(), mainAccount.getPassword());
        }
        QuestionsPage questionsPage = mainPage.chooseCategory(category);
        questionsPage.sendAnswers(answerMessage);
        LOG.info("No more questions to send answer to.");

        LOG.info("##################################");
        LOG.info("Bot finished sending answers.");
        LOG.info("##################################");
        driver.quit();
    }

    private List<String> getTextFromFile(String messageFileName) throws Exception {
        return Files.readAllLines(Paths.get(messageFileName));
    }

}
