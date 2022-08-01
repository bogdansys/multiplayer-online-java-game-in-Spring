package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;

public class GamemodeSelectionController {

    public final MainCtrl mainCtrl;

    /**
     * Constructor.
     * @param mainCtrl
     */
    @Inject
    public GamemodeSelectionController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Shows main menu.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Shows name input scene.
     */
    public void startSingleplayer() {
        mainCtrl.showSinglePlayerNameInput();
    }

    /**
     * Shows game ID input scene.
     */
    public void joinLobby() {
        mainCtrl.showGameIDInput();
    }

    /**
     * Shows name input scene.
     */
    public void createLobby() {
        mainCtrl.showNameInput();
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
