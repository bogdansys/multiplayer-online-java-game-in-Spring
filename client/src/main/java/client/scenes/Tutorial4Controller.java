package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;

public class Tutorial4Controller {

    public final MainCtrl mainCtrl;

    /**
     * Constructor.
     * @param mainCtrl
     */
    @Inject
    public Tutorial4Controller(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Shows main menu scene.
     */
    public void showMainMenu() {
        mainCtrl.setResizable(true);
        mainCtrl.showMainMenu();
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
