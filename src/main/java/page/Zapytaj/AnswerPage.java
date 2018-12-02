package page.Zapytaj;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AnswerPage {
    private static final String ANSWER_INPUT_TEXT_CSS = "textarea[id='questionContents']";
    private static final String SEND_ANSWER_BUTTON_CSS = "input[name='AddAnswer']";

    private static final Logger LOG = LoggerFactory.getLogger(AnswerPage.class);

    private final WebDriver driver;
    private WebDriverWait wait;
    private String pageHandler;

    @FindBy(css = ANSWER_INPUT_TEXT_CSS)
    private WebElement answerInput;

    @FindBy(css = SEND_ANSWER_BUTTON_CSS)
    private WebElement sendAnswerButton;


    public AnswerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageHandler = driver.getWindowHandle();
        wait = new WebDriverWait(driver, 10);
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
