package pages.zapytaj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.BasicPage;

public class LoginPage extends BasicPage {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    private static final String EMAIL_INPUT_CSS = "input[name='login[login]']";
    private static final String PASSWORD_INPUT_CSS = "input[name='login[password]']";
    private static final String REMEMBER_ME_CHECKBOX_CSS = "input[name='login[remember_me]']";
    private static final String LOGIN_BUTTON_CSS = "input[name='zaloguj']";

    @FindBy(css = EMAIL_INPUT_CSS)
    private WebElement emailInput;

    @FindBy(css = PASSWORD_INPUT_CSS)
    private WebElement passwordInput;

    @FindBy(css = REMEMBER_ME_CHECKBOX_CSS)
    private WebElement rememberMeCheckbox;

    @FindBy(css = LOGIN_BUTTON_CSS)
    private WebElement loginButton;


    public LoginPage(WebDriver driver) {
        super(driver);
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
}
