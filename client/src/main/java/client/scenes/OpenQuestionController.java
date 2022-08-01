package client.scenes;

import client.utils.QuestionUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OpenQuestionController extends QuestionController implements Initializable {

    @FXML
    public TextField answerTextField;
    @FXML
    public Button submitButton;
    @FXML
    public ImageView imageView;
    @FXML
    public Label yourAnswerLabel;
    @FXML
    public Label correctAnswerValueLabel;
    @FXML
    public Label correctAnswerLabel;

    public ClosedQuestionController closedQuestionController;

    /**
     * Constructor for OpenQuestionController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     * @param questionUtils a QuestionUtils object
     * @param settingsController a SettingsController object
     */
    @Inject
    public OpenQuestionController(MainCtrl mainCtrl, ServerUtils server, QuestionUtils questionUtils, SettingsController settingsController) {
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

        Image image = new Image("ActivityBankImages/activity-bank/" + questionUtils.currentQuestion.images.get(0));
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(0.4 * mainCtrl.getClosedQuestionSceneWidth());

        correctAnswerValueLabel.setVisible(false);
        correctAnswerLabel.setVisible(false);
        yourAnswerLabel.setVisible(false);
        submitButton.setVisible(true);
        answerTextField.setEditable(true);

        questionUtils.answered = false;
        questionUtils.playersAnswered = 0;
        questionUtils.correctAnswerShown = false;
        player.chosenAnswer = null;
        setPlayersAnswered();

        player.incrementQuestionNumber();
        questionNumberLabel.setText("Question " + player.questionNumber + "/20");
        playerScoreLabel.setText("Score: " + player.score);
        answerTextField.clear();
        submitButton.setDisable(false);
        resetProgressBar(20000);
        player.startTimer();
    }

    /**
     * Shows the correct answer by highlighting the correct answer green
     * If the player had chosen a wrong answer it is highlighted red.
     */
    public void showCorrectAnswer() {
        correctAnswerValueLabel.setVisible(true);
        correctAnswerLabel.setVisible(true);
        yourAnswerLabel.setVisible(true);
        submitButton.setVisible(false);

        correctAnswerValueLabel.setText(questionUtils.currentQuestion.correctAnswer);

        super.showCorrectAnswer();
    }

    /**
     * The method is called whenever an option button is pressed.
     * Checks whether the answer chosen by the user is correct and updates the score in Player class if so.
     *
     * @param event OnClick Button event
     */
    public void markAnswer(ActionEvent event) {
        String answer = answerTextField.getText();
        // return if answer is not a number
        // a label that informs the user that their answer is invalid could be shown in that case
        if (questionUtils.answered || questionUtils.correctAnswerShown || !answer.matches("^(?:[1-9]\\d*|0)$")) return;
        player.chosenAnswer = answer;
        server.send("/app/playerAnswered/" + player.lobbyID, true);
        updateScore();
        questionUtils.answered = true;
        answerTextField.setEditable(false);
        submitButton.setDisable(true);
    }

    /**
     * Method unused for OpenQuestionController.
     */
    public void timeRanOut() {
        // does nothing for open question
    }

    /**
     * Increases the score of a player based on time left and accuracy.
     */
    public void updateScore() {
        double errorFactor =
                Double.parseDouble(answerTextField.getText()) / Double.parseDouble(questionUtils.currentQuestion.correctAnswer);
        if (errorFactor > 1) {
            errorFactor = 1 / errorFactor;
        }
        player.score += player.doublePoints.getMultiplier() * Math.min(100, ((player.timeLeft + 1) * 10)) * errorFactor;
        player.doublePoints.setMultiplier1();
    }

    /**
     * Does nothing for open question.
     *
     * @param event the onAction event of the button being pressed
     */
    public void useAnswerJoker(ActionEvent event) {

    }

    /**
     * Resizes UI elements according to the window width.
     *
     * @param width width of the window
     */
    public void resizeUIElements(Number width) {
        super.resizeUIElements(width);
        imageView.setFitWidth(0.25 * width.intValue());
        imageView.setFitHeight(0.2 * width.intValue());
    }

    /**
     * Switches to an open question scene.
     */
    public void switchToOpenQuestionScene() {
        updateQuestion();
    }

    /**
     * Switches to a closed question scene.
     */
    public void switchToClosedQuestionScene() {
        player.questionController = closedQuestionController;
        mainCtrl.showClosedQuestion();
    }
}
