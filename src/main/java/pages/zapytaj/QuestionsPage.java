package pages.zapytaj;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.BasicPage;
import util.QuestionsUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionsPage extends BasicPage {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionsPage.class);

    private static final String QUESTIONS_LINKS_CSS = "ul[class*='item-list'] li h3 a[href*='/Category']";
    private static final String NEXT_PAGE_BUTTON_CSS = "a[class='pagingx'][rel='next']";
    private static final String NITRO_POPUP_CSS = "div[id='nitro-block']";
    private static final String NITRO_POPUP_CLOSE_BUTTON_CSS = "span[id='nitro-close']";

    @FindBy(css = QUESTIONS_LINKS_CSS)
    private List<WebElement> questionsLinks;

    @FindBy(css = NEXT_PAGE_BUTTON_CSS)
    private WebElement nextPageButton;

    @FindBy(css = NITRO_POPUP_CSS)
    private WebElement nitroPopup;

    @FindBy(css = NITRO_POPUP_CLOSE_BUTTON_CSS)
    private WebElement nitroPopupCloseButton;


    public QuestionsPage(WebDriver driver) {
        super(driver);
    }

    public void sendAnswers(List<String> answerToSend) throws Exception {
        List<String> questionLinks = getQuestionLinks();
        for (String link : questionLinks) {
            if (!QuestionsUtil.isQuestionAlreadyAnswered(link)) {
                ((JavascriptExecutor) driver)
                        .executeScript("window.open('')");
                QuestionPage questionPage = switchToEmptyWindow();
                driver.get(link);
                try {
                    AnswerPage answerPage = questionPage.clickAnswerButton();
                    answerPage.addAnswer(answerToSend);
                } catch (Exception e) {
                    LOG.error("Could not add answer to question: " + link);
                    driver.close();
                }
                QuestionsUtil.saveAnsweredQuestion(link);
                driver.switchTo().window(pageHandler);
            }
        }
        if (moreQuestions()) {
            dissmissNitroPopupIfVisible();
            scrollToCenterOfElement(nextPageButton);
            nextPageButton.click();
            sendAnswers(answerToSend);
        }
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

    private QuestionPage switchToEmptyWindow() throws Exception {
        driverWait(2);
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (!driver.getCurrentUrl().contains("zapytaj.onet.pl")) {
                return new QuestionPage(driver);
            }
        }
        throw new Exception("New window was not found.");
    }

    public QuestionsPage dissmissNitroPopupIfVisible() {
        try {
            if (nitroPopup.isDisplayed()) {
                nitroPopupCloseButton.click();
            }
        } catch (Exception e) {

        }
        return this;
    }

}
