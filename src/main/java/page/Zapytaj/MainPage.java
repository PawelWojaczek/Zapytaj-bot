package page.Zapytaj;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainPage {
    private static final String LOGIN_BUTTON_CSS = "a[id='przyciskZalogujSie']";
    private static final String POPUP_CONTENT_CSS = "div[class*='cmp-popup_content']";
    private static final String POPUP_ACCEPT_BUTTON_CSS = "button[class*='cmp-intro_acceptAll']";
    private static final String CATEGORIES_LIST_CSS = "section[class*='category list-scroll'] ul li";
    private static final String PAGES_CSS = "div[class='pages']";
    private static final String NEXT_PAGE_BUTTON_CSS = "div[class='pages'] a[accesskey='x']:not(:nth-of-type(1))";

    private static final Logger LOG = LoggerFactory.getLogger(MainPage.class);

    private final WebDriver driver;
    private WebDriverWait wait;
    private String pageHandler;

    @FindBy(css = LOGIN_BUTTON_CSS)
    private WebElement loginButton;

    @FindBy(css = POPUP_CONTENT_CSS)
    private WebElement popupContent;

    @FindBy(css = POPUP_ACCEPT_BUTTON_CSS)
    private WebElement popupAcceptButton;

    @FindBy(css = CATEGORIES_LIST_CSS)
    private List<WebElement> categoriesList;

    @FindBy(css = PAGES_CSS)
    private WebElement pages;

    @FindBy(css = NEXT_PAGE_BUTTON_CSS)
    private WebElement nextPageButton;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageHandler = driver.getWindowHandle();
        wait = new WebDriverWait(driver, 10);
    }

    public QuestionsPage chooseCategory(String categoryToChoose) {
        for (WebElement category : categoriesList) {
            if (category.getText().contains(categoryToChoose)) {
                new Actions(driver).moveToElement(category).build().perform();
                scrollToCenterOfElement(category);
                category.findElement(By.cssSelector("a")).click();
                return new QuestionsPage(driver);
            }
        }
        throw new RuntimeException("Category: " + categoryToChoose + " not found");
    }

    private void scrollToCenterOfElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({\n" +
                        "            behavior: 'auto',\n" +
                        "            block: 'center',\n" +
                        "            inline: 'center'\n" +
                        "        });", element);
    }

    public boolean isLoginPossible() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {

        }
        return false;
    }

    public LoginPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        return new LoginPage(driver);
    }

    public MainPage dismissPopupIfVisible() {
        try {
            if (popupContent.isDisplayed()) {
                popupAcceptButton.click();
            }
        } catch (Exception e) {

        }
        return this;
    }

    private void switchToWindow(String linkToMatch) throws Exception {
        driverWait(2);
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getCurrentUrl().contains(linkToMatch)) {
                return;
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
