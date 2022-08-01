package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;

public class Tutorial3Controller {

    public final MainCtrl mainCtrl;

    /**
     * Constructor.
     * @param mainCtrl
     */
    @Inject
    public Tutorial3Controller(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Shows next tutorial scene.
     */
    public void nextTutorial() {
        mainCtrl.showTutorial4();
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
