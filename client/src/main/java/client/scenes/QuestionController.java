package client.scenes;

import client.utils.QuestionUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Tuple;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public abstract class QuestionController extends EmoteController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final QuestionUtils questionUtils;
    public final SettingsController settingsController;

    @FXML
    public Label questionLabel;
    @FXML
    public Label questionNumberLabel;
    @FXML
    public GridPane jokersGridPane;
    @FXML
    public HBox emotesHBox;
    @FXML
    public Label playerScoreLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Label timeLeftLabel;
    @FXML
    public Label playersAnsweredLabel;
    @FXML
    public ImageView muted;
    @FXML
    public Button reduceTimeButton;
    @FXML
    public Button doublePointsButton;
    @FXML
    public Button eliminateButton;

    /**
     * Constructor for OpenQuestionController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     * @param questionUtils a QuestionUtils object
     * @param settingsController a SettingsController object
     */
    @Inject
    public QuestionController(MainCtrl mainCtrl, ServerUtils server, QuestionUtils questionUtils, SettingsController settingsController) {
        super(mainCtrl, server);
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.questionUtils = questionUtils;
        this.settingsController = settingsController;

        questionUtils.answered = false;
        questionUtils.correctAnswerShown = false;
        questionUtils.playersAnswered = 0;
        questionUtils.progressBarTimeline = new Timeline();
    }

    /**
     * Sets all fields to enable a singleplayer game.
     */
    public void setSingleplayer() {
        playersAnsweredLabel.setVisible(false);
        jokersGridPane.setVisible(false);
        emotesHBox.setVisible(false);
        questionUtils.singleplayer = true;
        muted.setVisible(settingsController.sliderMusic.getValue() == 0);
    }

    /**
     * Sets all fields to enable a multiplayer game.
     */
    public void setMultiplayer() {
        playersAnsweredLabel.setVisible(true);
        jokersGridPane.setVisible(true);
        emotesHBox.setVisible(true);
        questionUtils.singleplayer = false;
        muted.setVisible(settingsController.sliderMusic.getValue() == 0);
    }

    /**
     * Shows settings.
     */
    public void showSettings() {
        mainCtrl.showSettings();
    }

    /**
     * Prepares the next question and calls the method to switch to the corresponding scene.
     */
    public void nextQuestion() {
        int x = mainCtrl.player.questionNumber;
        int y = mainCtrl.player.lobbyID;
        questionUtils.currentQuestion = server.getMultiplayerQuestions(new Tuple(String.valueOf(x), y));
        if (questionUtils.currentQuestion.answers == null) {
            switchToOpenQuestionScene();
        } else {
            switchToClosedQuestionScene();
        }
    }

    /**
     * Fetches the question from the server, updates the UI to display the question and answers and starts the timer.
     */
    public void updateQuestion() {
        updateJokerButtons();
    }

    /**
     * Shows the correct answer by highlighting the correct answer green
     * If the player had chosen a wrong answer it is highlighted red.
     */
    public void showCorrectAnswer() {
        player.timeLeft = 3;
        resetProgressBar(player.timeLeft * 1000);
        questionUtils.correctAnswerShown = true;
        player.startTimer();
    }

    /**
     * The method is called whenever an option button is pressed.
     * Checks whether the answer chosen by the user is correct and updates the score in Player class if so.
     *
     * @param event OnClick Button event
     */
    public abstract void markAnswer(ActionEvent event);

    /**
     * Useless method for QuestionController.
     */
    public abstract void timeRanOut();

    /**
     * Updates the time label.
     *
     * @param time time left for the current question
     */
    public void updateTime(int time) {
        timeLeftLabel.setText(String.valueOf(time));
    }

    /**
     * Updates the score of the player.
     */
    public abstract void updateScore();

    /**
     * Resets the progress bar.
     * Progress bar will smoothly go down from 100% to 0% over time specified as a parameter.
     *
     * @param time time it will take the progress bar to go down from 100% to 0% in milliseconds
     */
    public void resetProgressBar(int time) {
        updateJokerButtons();
        questionUtils.progressBarTimeline.stop();
        progressBar.setProgress(1);

        KeyValue keyValue = new KeyValue(progressBar.progressProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(new Duration(time), keyValue);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        questionUtils.progressBarTimeline = timeline;

        questionUtils.progressBarTimeline.play();
    }

    /**
     * Resets the progress bar.
     * Progress bar will smoothly go down to 0% over time specified as a parameter.
     * The starting point is determined by fullDuration parameter: the progress bar will be set to time / timeDuration and then go to 0%.
     *
     * @param time         time it will take the progress bar to go down to 0% in milliseconds
     * @param fullDuration total time it would take for the progress bar to go to 0% if it started at 100%.
     */
    public void resetProgressBar(int time, int fullDuration) {
        updateJokerButtons();
        questionUtils.progressBarTimeline.stop();
        progressBar.setProgress(((double) time) / fullDuration);

        KeyValue keyValue = new KeyValue(progressBar.progressProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(new Duration(time), keyValue);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        questionUtils.progressBarTimeline = timeline;

        questionUtils.progressBarTimeline.play();
    }

    /**
     * Changes the text of the label showing the number of players that have answered the
     * question at the top left of a multiplayer game.
     */
    public void setPlayersAnswered() {
        if (playersAnsweredLabel.isVisible()) {
            playersAnsweredLabel.setText("Players answered: " + questionUtils.playersAnswered + '/' + player.playersInGame);
        }
    }

    /**
     * On-click method of time reduction joker button.
     * Sends information about pressed time reduction joker to the server.
     * Marks the time reduction joker as used for the player who used it.
     */
    public void useTimeReductionJoker(ActionEvent event) {
        if (!player.reduceTime.used && !questionUtils.correctAnswerShown) {
            server.send("/app/jokers/" + player.lobbyID, new Tuple(player.name, 5));
            player.reduceTime.used = true;
            Button callerButton = (Button) event.getSource();
            callerButton.setOpacity(0.4);
            callerButton.setDisable(true);
        }
    }

    /**
     * The method linked to the respective button on the client. Changes the joker's
     * used variable to true, and doubles the player's points gained this round.
     *
     * @param event the onAction event of the button being pressed
     */
    public void usePointsJoker(ActionEvent event) {
        if (!player.doublePoints.used && !questionUtils.correctAnswerShown) {
            player.useDoublePoints();
            Button callerButton = (Button) event.getSource();
            callerButton.setOpacity(0.4);
            callerButton.setDisable(true);
        }
    }

    /**
     * The method linked to the respective button on the client. Changes the joker's
     * used variable to true, and disables a random wrong answer from the player's screen.
     *
     * @param event the onAction event of the button being pressed
     */
    public abstract void useAnswerJoker(ActionEvent event);

    /**
     * Registers the user for receiving information if other players had answered the question.
     * Increases playersAnswered counter when this type of message is received.
     */
    public void registerForPlayerAnsweredMessages() {
        server.registerForMessages("/topic/playerAnswered/" + player.lobbyID, Boolean.class, b -> {
            questionUtils.playersAnswered++;
            Platform.runLater(() -> questionUtils.setPlayersAnswered());
        });
    }

    /**
     * Used to send and receive TimeRanOutMessages.
     */
    public void registerForTimeRanOutMessages() {
        server.registerForMessages("/topic/timeRanOut/" + player.lobbyID, Boolean.class, b -> {
            questionUtils.playersTime0++;
        });
    }

    /**
     * Registers the user for receiving information if other players had disconnected.
     * Increases playersAnswered counter when this type of message is received.
     */
    public void registerForPlayerDisconnectedMessages() {
        server.registerForMessages("/topic/playerDisconnected/" + player.lobbyID,
                Boolean.class, b -> player.playersInGame--);
    }

    /**
     * Resizes UI elements according to the window width.
     *
     * @param wi width of the window
     */
    public void resizeUIElements(Number wi) {
        int windowWidth = wi.intValue();
        questionLabel.setStyle("-fx-font-size:" + (int) (0.018 * windowWidth)
                + ";-fx-background-color: #8BC34A;" +
                "-fx-background-radius: 1000;");
        questionNumberLabel.setStyle("-fx-font-size:" + (int) (0.025 * windowWidth));
        timeLeftLabel.setStyle("-fx-font-size:" + (int) (0.025 * windowWidth));
        playerScoreLabel.setStyle("-fx-font-size:" + (int) (0.025 * windowWidth));
        playersAnsweredLabel.setStyle("-fx-font-size:" + (int) (0.02 * windowWidth));
    }

    /**
     * Switches to open question scene if needed and displays the next question.
     */
    public abstract void switchToOpenQuestionScene();

    /**
     * Switches to closed question scene if needed and displays the next question.
     */
    public abstract void switchToClosedQuestionScene();

    /**
     * Updates the joker buttons.
     */
    private void updateJokerButtons() {
        if (player.reduceTime.used) {
            reduceTimeButton.setDisable(true);
            reduceTimeButton.setOpacity(0.4);
        } else {
            reduceTimeButton.setDisable(false);
            reduceTimeButton.setOpacity(1);
        }

        if (player.doublePoints.used) {
            doublePointsButton.setDisable(true);
            doublePointsButton.setOpacity(0.4);
        } else {
            doublePointsButton.setDisable(false);
            doublePointsButton.setOpacity(1);
        }

        if (player.eliminateAnswer.used) {
            eliminateButton.setDisable(true);
            eliminateButton.setOpacity(0.4);
        } else {
            eliminateButton.setDisable(false);
            eliminateButton.setOpacity(1);
        }
    }

    /**
     * Used to mute sound.
     */
    public void muteSound() {
        settingsController.musicOff();
        muted.setVisible(settingsController.sliderMusic.getValue() == 0);
    }
}