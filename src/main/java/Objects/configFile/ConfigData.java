package Objects.configFile;

import java.util.List;

public class ConfigData {
    private MainAccount mainAccount;
    private List<Account> upvoteQuestionAccounts;
    private String category;
    private String answerFileName;
    private String questionsFileName;

    public ConfigData(MainAccount mainAccount, List<Account> upvoteQuestionAccounts, String category, String answerFileName, String questionsFileName) {
        this.mainAccount = mainAccount;
        this.upvoteQuestionAccounts = upvoteQuestionAccounts;
        this.category = category;
        this.answerFileName = answerFileName;
        this.questionsFileName = questionsFileName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MainAccount getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(MainAccount mainAccount) {
        this.mainAccount = mainAccount;
    }

    public List<Account> getUpvoteQuestionAccounts() {
        return upvoteQuestionAccounts;
    }

    public void setUpvoteQuestionAccounts(List<Account> upvoteQuestionAccounts) {
        this.upvoteQuestionAccounts = upvoteQuestionAccounts;
    }

    public String getAnswerFileName() {
        return answerFileName;
    }

    public void setAnswerFileName(String answerFileName) {
        this.answerFileName = answerFileName;
    }

    public String getQuestionsFileName() {
        return questionsFileName;
    }

    public void setQuestionsFileName(String questionsFileName) {
        this.questionsFileName = questionsFileName;
    }
}
