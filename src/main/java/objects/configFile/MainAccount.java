package objects.configFile;

public class MainAccount {

    private String email;
    private String password;
    private String accountName;

    public MainAccount(String email, String password, String accountName) {
        this.email = email;
        this.password = password;
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
