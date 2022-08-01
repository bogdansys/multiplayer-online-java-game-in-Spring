package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CloseConfirmationController {

    public MainCtrl mainCtrl;

    @FXML
    private Label rejoinMsg;

    /**
     * Constructor.
     * @param mainCtrl
     */
    @Inject
    public CloseConfirmationController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Closes the application.
     */
    public void closeApplication() {
        mainCtrl.closeApplication();
    }

    /**
     * Closes the popup.
     */
    public void closePopup() {
        mainCtrl.closePopup();
    }

    /**
     * Shows the message that you can't rejoin the game.
     *
     * @param display true if called when in game
     */
    public void displayRejoinMsg(boolean display) {
        rejoinMsg.setVisible(display);
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
