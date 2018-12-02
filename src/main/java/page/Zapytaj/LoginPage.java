package page.Zapytaj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
    private static final String EMAIL_INPUT_CSS = "input[name='login[login]']";
    private static final String PASSWORD_INPUT_CSS = "input[name='login[password]']";
    private static final String REMEMBER_ME_CHECKBOX_CSS = "input[name='login[remember_me]']";
    private static final String LOGIN_BUTTON_CSS = "input[name='zaloguj']";

    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = EMAIL_INPUT_CSS)
    private WebElement emailInput;

    @FindBy(css = PASSWORD_INPUT_CSS)
    private WebElement passwordInput;

    @FindBy(css = REMEMBER_ME_CHECKBOX_CSS)
    private WebElement rememberMeCheckbox;

    @FindBy(css = LOGIN_BUTTON_CSS)
    private WebElement loginButton;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    public MainPage login(String email, String password) throws Exception {
        fillEmail(email);
        fillPassword(password);
        checkRememberMe();
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        return new MainPage(driver);
    }

    private void fillEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    private void fillPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    private void checkRememberMe() {
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
    }

    private void driverWait(int seconds) throws InterruptedException {
        synchronized (driver) {
            driver.wait(seconds * 1000);
        }
    }
}
