package objects;

import objects.configFile.Account;
import org.openqa.selenium.WebDriver;

/**
 * Class responsilbe for keeping account instances (browser + account)
 */
public class BrowserAccountDriver {

    private WebDriver driver;
    private Account account;
    private String pageHandler;

    public BrowserAccountDriver(WebDriver driver, Account account, String pageHandler) {
        this.driver = driver;
        this.account = account;
        this.pageHandler = pageHandler;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPageHandler() {
        return pageHandler;
    }

    public void setPageHandler(String pageHandler) {
        this.pageHandler = pageHandler;
    }
}
