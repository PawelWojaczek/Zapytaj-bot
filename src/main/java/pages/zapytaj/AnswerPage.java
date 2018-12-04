package pages.zapytaj;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.BasicPage;

import java.util.List;

public class AnswerPage extends BasicPage {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerPage.class);

    private static final String ANSWER_INPUT_TEXT_CSS = "textarea[id='questionContents']";
    private static final String SEND_ANSWER_BUTTON_CSS = "input[name='AddAnswer']";

    @FindBy(css = ANSWER_INPUT_TEXT_CSS)
    private WebElement answerInput;

    @FindBy(css = SEND_ANSWER_BUTTON_CSS)
    private WebElement sendAnswerButton;


    public AnswerPage(WebDriver driver) {
        super(driver);
    }

    public void addAnswer(List<String> answerLines) throws Exception {
        for (String line : answerLines) {
            answerInput.sendKeys(line);
            answerInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.ENTER));
        }
        clickSendAnswerButton();
        driverWait(15);
        driver.close();
    }

    private void clickSendAnswerButton() {
        wait.until(ExpectedConditions.elementToBeClickable(sendAnswerButton));
        scrollToCenterOfElement(sendAnswerButton);
        sendAnswerButton.click();
    }
}
