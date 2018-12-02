package page.Zapytaj;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuestionPage {
    private static final String ADD_ANSWER_BUTTON_CSS = "div[class='answer-buttons'] a";
    private static final String ANSWERS_LIST_CSS = "article[class*='answer']";
    private static final String USER_INFO_NAME_CSS = "div[class='user-info'] a";
    private static final String UPVOTE_ANSWER_BUTTON_CSS = "div[class*='vote-up'] a";


    private static final Logger LOG = LoggerFactory.getLogger(QuestionPage.class);

    private final WebDriver driver;
    private WebDriverWait wait;
    private String pageHandler;

    @FindBy(css = ADD_ANSWER_BUTTON_CSS)
    private WebElement addAnswerButton;

    @FindBy(css = ANSWERS_LIST_CSS)
    private List<WebElement> answersList;


    public QuestionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageHandler = driver.getWindowHandle();
        wait = new WebDriverWait(driver, 10);
    }

    public AnswerPage clickAnswerButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addAnswerButton));
        scrollToCenterOfElement(addAnswerButton);
        addAnswerButton.click();
        return new AnswerPage(driver);
    }

    public void upvoteAnswer(String userToUpvote) {
        for (WebElement answer : answersList) {
            if (answer.findElement(By.cssSelector(USER_INFO_NAME_CSS)).getText().equalsIgnoreCase(userToUpvote)) {
                WebElement upvoteButton = answer.findElement(By.cssSelector(UPVOTE_ANSWER_BUTTON_CSS));
                wait.until(ExpectedConditions.elementToBeClickable(upvoteButton));
                scrollToCenterOfElement(upvoteButton);
                upvoteButton.click();
            }
        }
        throw new RuntimeException("No answer found to upvote");
    }

    private void scrollToCenterOfElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({\n" +
                        "            behavior: 'auto',\n" +
                        "            block: 'center',\n" +
                        "            inline: 'center'\n" +
                        "        });", element);
    }

    private void driverWait(int seconds) throws InterruptedException {
        synchronized (driver) {
            driver.wait(seconds * 1000);
        }
    }
}
