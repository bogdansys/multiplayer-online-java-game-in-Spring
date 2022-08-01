package client.scenes;

import client.utils.QuestionUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClosedQuestionController extends QuestionController implements Initializable {

    public List<Button> buttons;
    public List<ImageView> imageViews;

    @FXML
    private GridPane buttonsGridPane;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;

    public OpenQuestionController openQuestionController;

    /**
     * Constructor for ClosedQuestionController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     * @param questionUtils a QuestionUtils object
     * @param settingsController a SettingsController object
     */
    @Inject
    public ClosedQuestionController(MainCtrl mainCtrl, ServerUtils server, QuestionUtils questionUtils, SettingsController settingsController) {
        super(mainCtrl, server, questionUtils, settingsController);
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons = buttonsGridPane.getChildren().stream().map(x -> (Button) x).collect(Collectors.toList());
        imageViews = new ArrayList<>();
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        viewList.add(emojiView0);
        viewList.add(emojiView1);
        viewList.add(emojiView2);
        viewList.add(emojiView3);
        viewList.add(emojiView4);
        viewList.add(emojiView5);
        viewList.add(emojiView6);
        viewList.add(emojiView7);
        viewList.add(emojiView8);
        viewList.add(emojiView9);
    }

    /**
     * Fetches the question from the server, updates the UI to display the question and answers and starts the timer.
     */
    @Override
    public void updateQuestion() {
        super.updateQuestion();
        questionLabel.setText(questionUtils.currentQuestion.question);

        for (int i = 0; i < questionUtils.currentQuestion.images.size(); i++) {
            Image image = new Image("ActivityBankImages/activity-bank/" + questionUtils.currentQuestion.images.get(i));
            ImageView imageView = imageViews.get(i);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(0.1 * mainCtrl.getClosedQuestionSceneWidth());
        }

        List<String> answers = questionUtils.currentQuestion.answers;

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);

            button.setText(answers.get(i));
            button.getStyleClass().remove("marked-button");
            button.getStyleClass().removeAll("wrong-answer", "correct-answer");
            button.setDisable(false);
            button.setStyle("-fx-font-size:" + (int) (0.013 * mainCtrl.getClosedQuestionSceneWidth()));
        }

        questionUtils.answered = false;
        questionUtils.playersAnswered = 0;
        questionUtils.correctAnswerShown = false;
        player.chosenAnswer = null;
        setPlayersAnswered();

        player.incrementQuestionNumber();
        questionNumberLabel.setText("Question " + player.questionNumber + "/20");
        playerScoreLabel.setText("Score: " + player.score);
        resetProgressBar(20000);
        player.startTimer();
    }

    /**
     * Shows the correct answer by highlighting the correct answer green
     * If the player had chosen a wrong answer it is highlighted red.
     */
    public void showCorrectAnswer() {
        for (Button button : buttons) {
            if (questionUtils.currentQuestion.correctAnswer.equals(button.getText())) {
                button.getStyleClass().add("correct-answer");
            } else if (player.chosenAnswer != null && player.chosenAnswer.equals(button.getText())) {
                button.getStyleClass().add("wrong-answer");
            }
        }
        super.showCorrectAnswer();
    }

    /**
     * The method is called whenever an option button is pressed.
     * Checks whether the answer chosen by the user is correct and updates the score in Player class if so.
     *
     * @param event OnClick Button event
     */
    public void markAnswer(ActionEvent event) {
        if (questionUtils.correctAnswerShown) return;
        Button callerButton = (Button) event.getSource();
        if (!questionUtils.answered) {
            callerButton.getStyleClass().add("marked-button");
            player.chosenAnswer = callerButton.getText();
            server.send("/app/playerAnswered/" + player.lobbyID, true);
        }
        if (!questionUtils.answered && questionUtils.currentQuestion.correctAnswer.equals(callerButton.getText())) {
            updateScore();
        }
        questionUtils.answered = true;
    }

    /**
     * if the time has ran out the answer button will be disabled.
     */
    public void timeRanOut() {
        if (player.playersInGame - 1 != questionUtils.playersTime0) {
            for (Button button : buttons) {
                button.setDisable(true);
            }
        }
    }

    /**
     * Increases the score of a player by 5 times the time left.
     */
    public void updateScore() {
        player.score += player.doublePoints.getMultiplier() * Math.min(100, ((player.timeLeft + 1) * 5));
        player.doublePoints.setMultiplier1();
    }

    /**
     * The method linked to the respective button on the client. Changes the joker's
     * used variable to true, and disables a random wrong answer from the player's screen.
     *
     * @param event the onAction event of the button being pressed
     */
    public void useAnswerJoker(ActionEvent event) {
        if (!player.eliminateAnswer.used && !questionUtils.correctAnswerShown) {
            player.useEliminateAnswer();
            List<Integer> wrongAnswers = new ArrayList<>();
            int n = 0;
            for (String x : questionUtils.currentQuestion.answers) {
                if (!x.equals(questionUtils.currentQuestion.correctAnswer)) {
                    wrongAnswers.add(n);
                }
                n++;
            }
            Random rnd = new Random();
            int ansIndex = rnd.nextInt(2);
            Button wrongAnsButton = buttons.get(wrongAnswers.get(ansIndex));
            wrongAnsButton.setDisable(true);
            Button callerButton = (Button) event.getSource();
            callerButton.setOpacity(0.4);
            callerButton.setDisable(true);
        }
    }

    /**
     * Resizes UI elements according to the window width.
     *
     * @param width width of the window
     */
    public void resizeUIElements(Number width) {
        super.resizeUIElements(width);
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = imageViews.get(i);
            imageView.setFitWidth(0.1 * width.intValue());
            Button button = buttons.get(i);
            button.setStyle("-fx-font-size:" + (int) (0.013 * width.intValue()));
        }
    }

    /**
     * Switches to an open question scene.
     */
    public void switchToOpenQuestionScene() {
        player.questionController = openQuestionController;
        mainCtrl.showOpenQuestion();
    }

    /**
     * Switches to a closed question scene.
     */
    public void switchToClosedQuestionScene() {
        updateQuestion();
    }
}
