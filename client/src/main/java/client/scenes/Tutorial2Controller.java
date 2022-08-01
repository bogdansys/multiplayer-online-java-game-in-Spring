package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;

public class Tutorial2Controller {

    public final MainCtrl mainCtrl;

    /**
     * Constructor.
     * @param mainCtrl
     */
    @Inject
    public Tutorial2Controller(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Shows next tutorial scene.
     */
    public void nextTutorial() {
        mainCtrl.showTutorial3();
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
