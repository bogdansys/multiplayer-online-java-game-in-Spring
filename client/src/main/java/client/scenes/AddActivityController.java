package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddActivityController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;

    @FXML
    public Label header;
    @FXML
    public TextField id;
    @FXML
    public TextField title;
    @FXML
    public TextField energy;
    @FXML
    public TextField imagePath;
    @FXML
    public TextField source;
    @FXML
    public Button button;

    @FXML
    private Label errorID;
    @FXML
    private Label errorEnergy;
    @FXML
    private Label errorImage;

    /**
     * Constructor.
     * @param mainCtrl
     * @param server
     */
    @Inject
    public AddActivityController(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Shows admin panel.
     */
    public void showAdminPanel() {
        mainCtrl.showAdminPanel();
    }

    /**
     * Sends an activity with the given information to the server
     * if any field is wrong it gives the corresponding error.
     */
    public void confirm() {
        resetErrors();
        try {
            String id = this.id.getText();
            if (id.contains(" ")) throw new IllegalArgumentException();
            String title = this.title.getText();
            int energy = Integer.parseInt(this.energy.getText());
            String imagePath = this.imagePath.getText();
            if (imagePath.contains(" ")) throw new IllegalStateException();
            String source = this.source.getText();
            if (server.postActivity(new Activity(id, imagePath, title, energy, source))) {
                mainCtrl.addedSuccessfully();
                showAdminPanel();
            }
            mainCtrl.closePopup();
        } catch (NumberFormatException e) {
            errorEnergy.setVisible(true);
        } catch (IllegalArgumentException e) {
            errorID.setVisible(true);
        } catch (IllegalStateException e) {
            errorImage.setVisible(true);
        }
    }

    /**
     * Sets all error messages to invisible.
     */
    public void resetErrors() {
        errorID.setVisible(false);
        errorEnergy.setVisible(false);
        errorImage.setVisible(false);
    }

    /**
     * Plays click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
