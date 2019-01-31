package pages.components;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasicPage {
    protected WebDriver driver;
    protected String pageHandler;
    protected WebDriverWait wait;

    public BasicPage(WebDriver driver) {
        this.driver = driver;
        this.pageHandler = driver.getWindowHandle();
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 10);
    }

    protected void driverWait(int seconds) throws InterruptedException {
        synchronized (driver) {
            driver.wait(seconds * 1000);
        }
    }

    protected void scrollToCenterOfElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({\n" +
                        "            behavior: 'auto',\n" +
                        "            block: 'center',\n" +
                        "            inline: 'center'\n" +
                        "        });", element);
    }
}
