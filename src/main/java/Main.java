import Worker.AnswerBotWorker;
import Worker.MainWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static Logger LOG = LoggerFactory.getLogger(AnswerBotWorker.class);

    public synchronized static void main(String[] args) throws Exception {
        try {
            LOG.info("Zapytaj.onet Spambot by Pawlosek");
            MainWorker mainWorker = new MainWorker();
            mainWorker.start();

        } catch (Exception e) {
            LOG.error("UNEXPECTED ERROR. BOT STOPPED. ", e);
        }
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
}
