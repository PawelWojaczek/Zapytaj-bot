package Objects;

import Objects.configFile.Account;
import org.openqa.selenium.WebDriver;

public class AccountDriver {

    private WebDriver driver;
    private Account account;
    private String pageHandler;

    public AccountDriver(WebDriver driver, Account account, String pageHandler) {
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
