package client;

import client.scenes.MainCtrl;
import client.scenes.QuestionController;
import client.utils.ServerUtils;
import client.utils.SoundUtils;
import commons.DoublePoints;
import commons.EliminateAnswer;
import commons.TimeReduction;
import commons.Tuple;
import jakarta.inject.Inject;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Player {

    public String name;
    public int score;
    public int timeLeft;
    public int questionNumber;
    public String chosenAnswer;
    public int lobbyID;
    public int playersInGame;
    public boolean timeRanOut;

    public DoublePoints doublePoints;
    public TimeReduction reduceTime;
    public EliminateAnswer eliminateAnswer;

    public MainCtrl mainCtrl;
    public QuestionController questionController;
    public ServerUtils server;

    /**
     * A default constructor for the player class without the dependencies.
     */
    public Player() {
    }

    /**
     * Constructs a player.
     *
     * @param name               the username
     * @param mainCtrl           the main controller of the game
     * @param questionController the controller of the question scene
     */
    @Inject
    public Player(String name, MainCtrl mainCtrl, QuestionController questionController, ServerUtils server) {
        this.name = name;
        this.mainCtrl = mainCtrl;
        this.questionController = questionController;
        this.server = server;
    }

    /**
     * This method resets all the parameters in the player class and then the starts the round.
     */
    public void startRound() {
        setParams();
        questionController.registerForTimeRanOutMessages();
        questionController.registerForPlayerAnsweredMessages();
        questionController.registerForPlayerDisconnectedMessages();
        questionController.nextQuestion();
    }

    /**
     * Resets all the parameters in the player class to the default value's.
     */
    public void setParams() {
        questionNumber = 0;
        score = 0;
        timeLeft = 20;
        timeRanOut = false;
        playersInGame = server.getPlayersInGame(lobbyID);
        doublePoints = new DoublePoints();
        reduceTime = new TimeReduction();
        eliminateAnswer = new EliminateAnswer();
    }

    /**
     * Decreases timeLeft every second until it hits 0 and shows the leaderboard after 20 questions.
     * Checks if someone used a time reduction joker every second.
     */
    public void startTimer() {
        Timer timer = new Timer();
        server.timeReductionReset();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update(timer);
            }
        }, 0, 1000);
    }

    /**
     * Game loop update function.
     * Checks if the game has ended or if it is possible to move on to the next question (or to show correct answer)
     * and calls adequate methods.
     *
     * @param timer timer which schedules the game update
     */
    public void update(Timer timer) {
        if (timeLeft-- <= 0) {
            if (!questionController.questionUtils.correctAnswerShown) {
                if (!timeRanOut) {
                    Platform.runLater(() -> server.send("/app/timeRanOut/" + lobbyID, true));
                    Platform.runLater(questionController::timeRanOut);
                    Platform.runLater(() -> SoundUtils.playSFX("gong"));
                    timeRanOut = true;
                }
                timeLeft = 0;
            } else {
                if (questionNumber < 20) {
                    showNext();
                } else {
                    endGame();
                }
                timer.cancel();
            }
        }
        if (timeLeft == 4 && !questionController.questionUtils.correctAnswerShown && !questionController.questionUtils.answered) {
            Platform.runLater(() -> SoundUtils.playSFX("ticking"));
        }
        if (playersInGame == questionController.questionUtils.playersTime0 || playersInGame == questionController.questionUtils.playersAnswered) {
            showNext();
            timer.cancel();
        }
        Platform.runLater(() -> questionController.updateTime(timeLeft + 1));
    }

    /**
     * Shows correct answer or moves on to the next question.
     */
    public void showNext() {
        if (questionController.questionUtils.correctAnswerShown) {
            Platform.runLater(questionController::nextQuestion);
        } else {
            Platform.runLater(() -> {
                questionController.setPlayersAnswered();
                questionController.questionUtils.playersAnswered = 0;
                questionController.questionUtils.playersTime0 = 0;
                timeRanOut = false;
            });
            Platform.runLater(questionController::showCorrectAnswer);
        }
        resetTime();
    }

    /**
     * Shows the final leaderboard.
     * Communicates the score and game state to the server.
     */
    public void endGame() {

        Tuple data = new Tuple(name, lobbyID, score);
        server.updateScoreOfGamer(data);
        server.disableGame(lobbyID);
        if (questionController.questionUtils.singleplayer) {
            Platform.runLater(mainCtrl::showSingleplayerEndLeaderboard);
        } else {
            Platform.runLater(mainCtrl::showMultiplayerEndLeaderboard);
        }
    }

    /**
     * Reduces timeLeft by 5 if the name of the player who used the time reduction joker doesn't match their name
     * timeLeft will never get reduced under 5 (seconds)
     * Updates the progressbar and time left in the UI.
     */
    public void reduceTime(Tuple timeJokerUser) {
        if (timeJokerUser.name != null && !timeJokerUser.name.equals(name)) {
            timeLeft = Math.max(timeLeft - 5, 5);
            questionController.resetProgressBar(1000 * timeLeft, 20000);
            questionController.updateTime(timeLeft);
        }
    }

    /**
     * Resets timeLeft to 20.
     */
    public void resetTime() {
        timeLeft = 20;
    }

    /**
     * Increments questionNumber.
     */
    public void incrementQuestionNumber() {
        questionNumber++;
    }

    /**
     * Returns if the "double points" joker has been used or not.
     *
     * @return doublePoints
     */
    public boolean getDoublePoints() {
        return doublePoints.used;
    }

    /**
     * Uses the double points joker.
     */
    public void useDoublePoints() {
        if (!getDoublePoints()) {
            doublePoints.use();
        }
    }

    /**
     * Returns if the "eliminate answer" joker has been used or not.
     *
     * @return eliminateAnswer
     */
    public boolean isEliminateAnswerUsed() {
        return eliminateAnswer.used;
    }

    /**
     * Uses the eliminate answer joker.
     */
    public void useEliminateAnswer() {
        eliminateAnswer.use();
    }

    /**
     * Disconnects the player from the game.
     */
    public void disconnect() {
        if (lobbyID != 0) {
            server.disconnect(new Tuple(name, lobbyID));
            server.send("/app/playerDisconnected/" + lobbyID, true);
        }
    }
}
