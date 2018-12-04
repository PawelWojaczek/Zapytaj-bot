package pages.zapytaj;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.BasicPage;

import java.util.List;

public class QuestionPage extends BasicPage {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionPage.class);

    private static final String ADD_ANSWER_BUTTON_CSS = "div[class='answer-buttons'] a";
    private static final String ANSWERS_LIST_CSS = "article[class*='answer']";
    private static final String USER_INFO_NAME_CSS = "div[class='user-info'] a";
    private static final String UPVOTE_ANSWER_BUTTON_CSS = "div[class*='vote-up'] a";

    @FindBy(css = ADD_ANSWER_BUTTON_CSS)
    private WebElement addAnswerButton;

    @FindBy(css = ANSWERS_LIST_CSS)
    private List<WebElement> answersList;


    public QuestionPage(WebDriver driver) {
        super(driver);
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
}
