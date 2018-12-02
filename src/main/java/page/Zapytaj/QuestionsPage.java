package page.Zapytaj;

import Objects.QuestionsUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class QuestionsPage {
    private static final String QUESTIONS_LINKS_CSS = "ul[class*='item-list'] li h3 a[href*='/Category']";
    private static final String NEXT_PAGE_BUTTON_CSS = "a[class='pagingx'][rel='next']";

    private static final Logger LOG = LoggerFactory.getLogger(QuestionsPage.class);

    private final WebDriver driver;
    private WebDriverWait wait;
    private String pageHandler;

    @FindBy(css = QUESTIONS_LINKS_CSS)
    private List<WebElement> questionsLinks;

    @FindBy(css = NEXT_PAGE_BUTTON_CSS)
    private WebElement nextPageButton;


    public QuestionsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageHandler = driver.getWindowHandle();
        wait = new WebDriverWait(driver, 10);
    }

    public void sendAnswers(List<String> answerToSend) throws Exception {
        List<String> questionLinks = getQuestionLinks();
        for (String link : questionLinks) {
            if (!QuestionsUtil.isQuestionAlreadyAnswered(link)) {
                ((JavascriptExecutor) driver)
                        .executeScript("window.open('')");
                QuestionPage questionPage = switchToEmptyWindow(link);
                driver.get(link);
                AnswerPage answerPage = questionPage.clickAnswerButton();
                answerPage.addAnswer(answerToSend);
                QuestionsUtil.saveAnsweredQuestion(link);
                driver.switchTo().window(pageHandler);
            }
        }
        if (moreQuestions()) {
            nextPageButton.click();
            sendAnswers(answerToSend);
        }
    }

    private void scrollToCenterOfElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({\n" +
                        "            behavior: 'auto',\n" +
                        "            block: 'center',\n" +
                        "            inline: 'center'\n" +
                        "        });", element);
    }

    public List<String> getQuestionLinks() {
        List<String> questionLinks = new ArrayList<>();
        for (WebElement questionLink : questionsLinks) {
            String link = questionLink.getAttribute("href");
            questionLinks.add(link);
        }
        return questionLinks;
    }

    private boolean moreQuestions() {
        try {
            WebElement nextPage = wait.until(ExpectedConditions.visibilityOf(nextPageButton));
            if (nextPage != null) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private QuestionPage switchToEmptyWindow(String linkToMatch) throws Exception {
        driverWait(2);
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (!driver.getCurrentUrl().contains("zapytaj.onet.pl")) {
                return new QuestionPage(driver);
            }
        }
        throw new Exception("New window was not found.");
    }

    private void driverWait(int seconds) throws InterruptedException {
        synchronized (driver) {
            driver.wait(seconds * 1000);
        }
    }
}
