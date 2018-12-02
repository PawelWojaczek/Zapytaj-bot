package Objects;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class QuestionsUtil {

    private static List<String> questionsAnswered;
    private static String questionsFileName;
    private static List<String> newQuestionsAnswered;


    public synchronized static void loadQuestionsFromFile(String fileName) throws Exception {
        questionsFileName = fileName;
        questionsAnswered = Files.readAllLines(Paths.get(fileName));
        newQuestionsAnswered = new ArrayList<>();
    }

    public synchronized static void saveAnsweredQuestion(String questionLink) throws IOException {
        questionsAnswered.add(questionLink);
        FileWriter fw = new FileWriter(questionsFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(questionLink);
        bw.newLine();
        bw.close();
        newQuestionsAnswered.add(questionLink);
    }

    public synchronized static boolean isQuestionAlreadyAnswered(String questionLink) {
        return questionsAnswered.stream().anyMatch(Predicate.isEqual(questionLink));
    }

    public synchronized static String getNewAnswerToUpvote() {
        if (!newQuestionsAnswered.isEmpty()) {
            String questionToUpvote = newQuestionsAnswered.get(0);
            newQuestionsAnswered.remove(questionToUpvote);
            return questionToUpvote;
        }
        return null;
    }


}
