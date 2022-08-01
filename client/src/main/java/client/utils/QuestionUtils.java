package client.utils;

import client.scenes.ClosedQuestionController;
import client.scenes.OpenQuestionController;
import commons.Question;
import javafx.animation.Timeline;

public class QuestionUtils {
    public Question currentQuestion;
    public boolean answered;
    public boolean correctAnswerShown;
    public int playersAnswered;
    public Timeline progressBarTimeline;
    public boolean singleplayer;
    public int playersTime0;
    public ClosedQuestionController closedQuestionController;
    public OpenQuestionController openQuestionController;

    /**
     * Updates players answered label.
     */
    public void setPlayersAnswered() {
        closedQuestionController.setPlayersAnswered();
        openQuestionController.setPlayersAnswered();
    }
}
