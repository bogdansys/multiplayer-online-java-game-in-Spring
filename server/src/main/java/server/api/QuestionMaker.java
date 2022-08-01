package server.api;

import commons.Activity;
import commons.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * a class that takes 3 questions from the database and creates a question from them.
 * If the client needs a new question, a request to QuestionMaker will send them a randomly generated question.
 */

@RestController
@RequestMapping("/api/question")
public class QuestionMaker {

    public static ActivityRepository repo;

    /**
     * Constructor for the QuestionMaker.
     * @param repo the activity repository
     */
    public QuestionMaker(ActivityRepository repo) {
        this.repo = repo;
    }

    /**
     * The method that generates the question by pulling 3 random activities from the database.
     *
     * @return a randomly generated Question
     */
    @GetMapping(path = {"", "/"})
    public static Question getQuestion() {

        //database of all
        List<Activity> databaseActivities = repo.findAll();

        //where we store the 3 random generated activities
        List<Activity> listActivities = new ArrayList<Activity>();

        Random random = new Random();

        boolean small = false;
        boolean medium = false;
        boolean large = false;

        while (listActivities.size() < 3) {
            int number = random.nextInt(databaseActivities.size());
            Activity temp = databaseActivities.get(number);

            if (listActivities.size() == 0) {
                if (temp.consumption_in_wh >= 20000) {
                    large = true;
                } else if (temp.consumption_in_wh >= 2000) {
                    medium = true;
                } else {
                    small = true;
                }
                listActivities.add(temp);
            } else if (listActivities.contains(temp)) ;
            else {
                if (temp.consumption_in_wh >= 20000 && large) {
                    listActivities.add(temp);
                } else if (20000 > temp.consumption_in_wh && temp.consumption_in_wh >= 1000 && medium) {
                    listActivities.add(temp);
                } else if (1000 > temp.consumption_in_wh && temp.consumption_in_wh >= 0 && small) {
                    listActivities.add(temp);
                }
            }
        }

        //choose question type at random
        switch (random.nextInt(3)) {
            case 0:
                return generateConsumesTheMost(listActivities);
            case 1:
                return generateHowMuch(listActivities);
            default:
                return generateInsteadOfXHowMuchY(listActivities);
        }
    }

    /**
     * An endpoint used to check how many activities are considered large, medium, and small.
     *
     * @return a list containing how many activities are considered large, medium, and small
     */
    @GetMapping(path = {"/ranges"})
    public static List<Integer> activityRanges() {
        List<Integer> result = new ArrayList<>();

        List<Activity> databaseActivities = repo.findAll();

        int l = 0;
        int m = 0;
        int s = 0;

        for (Activity x : databaseActivities) {
            if (x.consumption_in_wh >= 20000) {
                l++;
            } else if (x.consumption_in_wh >= 1000) {
                m++;
            } else {
                s++;
            }
        }

        result.add(l);
        result.add(m);
        result.add(s);

        return result;
    }

    /**
     * Generates "Which activity consumes the most energy?" type of question.
     *
     * @param listActivities list of three activities
     * @return a question generated based of the activities from listActivities
     */
    public static Question generateConsumesTheMost(List<Activity> listActivities) {
        //generate the correct answer
        int index = 0;
        for (int i = 1; i < 3; i++) {
            if (listActivities.get(index).consumption_in_wh < listActivities.get(i).consumption_in_wh) {
                index = i;
            }
        }
        //make a list of the activities
        String correctAnswer = listActivities.get(index).title;

        ArrayList<String> multipleChoice = new ArrayList<String>();

        ArrayList<String> images = new ArrayList<>();

        for (int i = 0; i < listActivities.size(); i++) {
            images.add(listActivities.get(i).image_path);
            multipleChoice.add(listActivities.get(i).title);
        }

        String phrase = "What activity consumes the most energy?";

        Question question = new Question(phrase, multipleChoice, correctAnswer, images);

        return question;
    }

    /**
     * Generates "Instead of X how much Y" type of question.
     *
     * @param listActivities list of three activities
     * @return a question generated based of the activities from listActivities
     */
    public static Question generateInsteadOfXHowMuchY(List<Activity> listActivities) {
        //select two activities
        Activity activity1 = listActivities.get(0);
        Activity activity2 = listActivities.get(1);

        //switch so that activity1 consumption is higher
        if (activity1.consumption_in_wh < activity2.consumption_in_wh) {
            Activity temp = activity1;
            activity1 = activity2;
            activity2 = temp;
        }

        //generate correct answer
        double correctAnswerDouble = (double) activity1.consumption_in_wh / activity2.consumption_in_wh;
        String correctAnswer = String.valueOf((int) correctAnswerDouble);

        //generate answer list
        ArrayList<String> multipleChoice = new ArrayList<>(3);
        multipleChoice.add(correctAnswer);
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int multiplierBound;
            if (random.nextInt(2) % 2 == 0) {
                //small differences
                multiplierBound = 3;
            } else {
                //big differences
                multiplierBound = 100;
            }
            double multiplier = random.nextInt(multiplierBound) + 0.3;
            double answer;
            if (random.nextInt(2) % 2 == 0) {
                answer = correctAnswerDouble * multiplier;
            } else {
                answer = correctAnswerDouble / multiplier;
            }
            if (answer < 1) {
                answer = (random.nextInt(10) + 2.3) * correctAnswerDouble;
            }
            if (multipleChoice.contains(String.valueOf((int) answer))) {
                i--;
                continue;
            }
            multipleChoice.add(String.valueOf((int) answer));
        }
        Collections.shuffle(multipleChoice);

        //add images
        ArrayList<String> images = new ArrayList<>(3);
        for (int i = 0; i < listActivities.size(); i++) {
            images.add(activity2.image_path);
        }

        //create question phrase
        String activity1Name = activity1.title.substring(0, 1).toLowerCase() + activity1.title.substring(1);
        String activity2Name = activity2.title.substring(0, 1).toLowerCase() + activity2.title.substring(1);
        String phrase = "Instead of " + activity1Name + " for how much more time could one spend " + activity2Name + "?";

        return new Question(phrase, multipleChoice, correctAnswer, images);
    }

    /**
     * Generates question in the form "How much energy does X consume?".
     *
     * @param listActivities activities to chose from
     * @return Question object
     */
    public static Question generateHowMuch(List<Activity> listActivities) {
        Activity activity = listActivities.get(0);
        String activityName = activity.title.substring(0, 1).toLowerCase() + activity.title.substring(1);
        String phrase = "How much energy (in Wh) does " + activityName + " consume?";
        String correctAnswer = String.valueOf(activity.consumption_in_wh);
        List<String> image = List.of(activity.image_path);
        return new Question(phrase, null, correctAnswer, image);
    }
}
