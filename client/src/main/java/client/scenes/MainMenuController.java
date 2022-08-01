package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class MainMenuController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;

    public Timer timer;

    @FXML
    private Label playersOnlineLabel;
    @FXML
    private Label gamesOngoingLabel;

    /**
     * Constructor.
     * @param mainCtrl
     * @param server
     */
    @Inject
    public MainMenuController(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Shows gamemode selection scene.
     */
    public void showGamemodeSelection() {
        mainCtrl.showGamemodeSelection();
        timer.cancel();
    }

    /**
     * Shows the first tutorial scene.
     */
    public void showTutorial() {
        mainCtrl.showTutorial1();
        timer.cancel();
    }

    /**
     * Shows name input scene before showing match history.
     */
    public void showNameInputMatchHistory() {
        mainCtrl.showNameInputMatchHistory();
        timer.cancel();
    }

    /**
     * Shows settings scene.
     */
    public void showSettings() {
        mainCtrl.showSettings();
        timer.cancel();
    }

    /**
     * Starts a timer that refreshes the statistics every 5 seconds.
     */
    public void staticsTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int playersOnline = server.getPlayersOnline();
                int gamesOngoing = server.getGamesOngoing();
                Platform.runLater(() -> updateStatistics(playersOnline, gamesOngoing));
            }
        }, 0, 5000);
    }

    /**
     * Updates the statistics on the screen.
     *
     * @param playersOnline the amount of players online
     * @param gamesOngoing  the amount of games currently ongoing on the server
     */
    public void updateStatistics(int playersOnline, int gamesOngoing) {
        playersOnlineLabel.setText("Players online: " + playersOnline);
        gamesOngoingLabel.setText("Games ongoing: " + gamesOngoing);
    }

    /**
     * Exits the application. Before that, the user has to confirm their choice.
     */
    public void exitApplication() {
        mainCtrl.showCloseConfirmation();
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}