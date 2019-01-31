package worker;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import objects.configFile.ConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.QuestionsUtil;

import java.io.FileReader;

/**
 * Worker that starts threads with answer and upvote workers.
 * Possible improvement: Since there is more than one upvoteBot possible, each one should be on another thread.
 */
public class MainWorker {
    private static Logger LOG = LoggerFactory.getLogger(AnswerBotWorker.class);

    private ConfigData configData;

    public void start() throws Exception {
        configData = getConfigFromFile();
        QuestionsUtil.loadQuestionsFromFile(configData.getQuestionsFileName());

        Runnable answerBotWorker = () -> {
            try {
                LOG.info("Initializing AnswerBot");
                new AnswerBotWorker(configData.getAnswerFileName(), configData.getMainAccount(), configData.getCategory()).start();
            } catch (Exception e) {
                LOG.error("AnswerBot error: ", e);
            }
        };

        Runnable upvoteBotWorker = () -> {
            try {
                LOG.info("Initializing UpvoteBot");
                new UpvoteAnswerWorker(configData.getMainAccount().getAccountName(), configData.getUpvoteQuestionAccounts()).start();
            } catch (Exception e) {
                LOG.error("UpvoteBot error: ", e);
            }
        };

        Thread thread1 = new Thread(answerBotWorker);
        Thread thread2 = new Thread(upvoteBotWorker);

        thread1.start();
        thread2.start();
    }

    private ConfigData getConfigFromFile() throws Exception {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("config.txt"));
        return gson.fromJson(reader, ConfigData.class);
    }
}
