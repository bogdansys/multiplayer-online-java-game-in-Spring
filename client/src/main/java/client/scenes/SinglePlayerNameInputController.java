package client.scenes;


import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;


public class SinglePlayerNameInputController {

    public final MainCtrl mainCtrl;
    public final ServerUtils serverUtils;
    public final Preferences pref;

    @FXML
    private TextField enterNameText;
    @FXML
    private Label labelOneWord;

    /**
     * Constructor for NameInputJoinLobbyController.
     * @param mainCtrl a MainCtrl object
     * @param serverUtils a ServerUtils object
     */
    @Inject
    public SinglePlayerNameInputController(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.pref = Preferences.userNodeForPackage(MainCtrl.class);
    }

    /**
     * Shows the GamemodeSelectionScene.
     */
    public void showGamemodeSelection() {
        labelOneWord.setVisible(false);
        mainCtrl.showGamemodeSelection();
        if (enterNameText != null) {
            pref.put("username", enterNameText.getText());
        }
    }

    /**
     * start a new game here , creating a game with one gamer
     * make an endpoint that creates a gamer and a game, connects them and then:
     * (the gamer won't have score yet)
     * we send a name, and we get back a unique id for that name, associated with a unique game (that can have 6 players at once).
     */
    public void startSinglePlayer() {
        labelOneWord.setVisible(false);
        String name = enterNameText.getText();
        if (name.equals("")) return;
        if (name.contains(" ")) {
            labelOneWord.setVisible(true);
            return;
        }
        mainCtrl.player.name = name;
        mainCtrl.player.lobbyID = serverUtils.sendNameToServer_SinglePlayer(name);
        pref.put("username", name);
        mainCtrl.startGame();
    }

    /**
     * Clears the gameIDInput FXML field.
     */
    public void clearField() {
        enterNameText.clear();
        enterNameText.requestFocus();
    }

    /**
     * Puts recent username used by the player in the name input text field.
     */
    public void updateNameTextField() {
        String userNamePreference = pref.get("username", "");
        enterNameText.setText(userNamePreference);
    }

    /**
     * Creates the clicksound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
