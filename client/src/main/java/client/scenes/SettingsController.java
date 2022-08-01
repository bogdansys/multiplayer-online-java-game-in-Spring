package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsController implements Initializable {

    public final MainCtrl mainCtrl;

    public final Timer timer;

    public double musicValue;
    public double sfxValue;
    public boolean emotes;

    @FXML
    public Slider sliderMusic;
    @FXML
    private Slider sliderSFX;
    @FXML
    private ProgressBar pbMusic;
    @FXML
    private ProgressBar pbSFX;
    @FXML
    private ImageView close;

    /**
     * Constructs a SettingsController.
     *
     * @param mainCtrl a MainCtrl object
     */
    @Inject
    public SettingsController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        timer = new Timer();
        emotes = true;
    }

    /**
     * Sets up all the things needed for the music to play and to control the volume.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SoundUtils.volumeMusic = sliderMusic.getValue() * 0.01;
        SoundUtils.volumeSFX = sliderSFX.getValue() * 0.01;
        SoundUtils.setupSound();

        sliderMusic.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                SoundUtils.volumeMusic = sliderMusic.getValue() * 0.01;
                SoundUtils.setMusicVolume();
                SoundUtils.playMusic();
            }
        });

        sliderSFX.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                SoundUtils.volumeSFX = sliderSFX.getValue() * 0.01;
                SoundUtils.playSFX("click");
            }
        });
    }

    /**
     * Switches to the main menu scene.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Switches to the admin panel scene.
     */
    public void showAdminPanel() {
        mainCtrl.showAdminPanel();
    }

    /**
     * When initiating a drag on any of the sliders,
     * this function starts a timer to update the progressbar accordingly every 50ms.
     */
    public void startSlider() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pbMusic.setProgress(sliderMusic.getValue() / 100);
                pbSFX.setProgress(sliderSFX.getValue() / 100);
            }
        }, 0, 50);
    }

    /**
     * When releasing the slider, this should stop the timer so that it doesn't keep running infinitely.
     */
    public void stopSliderMusic() {
        timer.cancel();
    }

    /**
     * When releasing the slider, this should stop the timer so that it doesn't keep running infinitely.
     */
    public void stopSliderSFX() {
        SoundUtils.playSFX("click");
        timer.cancel();
    }

    /**
     * This toggles the music slider value to off and stores the previous value in a variable.
     * If it's already off when clicked, it gets restored back to the previous value.
     */
    public void musicOff() {
        if (sliderMusic.getValue() == 0) {
            sliderMusic.setValue(musicValue);
            pbMusic.setProgress(musicValue / 100);
            SoundUtils.playMusic();
        } else {
            musicValue = sliderMusic.getValue();
            sliderMusic.setValue(0);
            pbMusic.setProgress(0);
            SoundUtils.pauseMusic();
        }
    }

    /**
     * This toggles the sfx slider value to off and stores the previous value in a variable.
     * If it's already off when clicked, it gets restored back to the previous value.
     */
    public void sfxOff() {
        if (sliderSFX.getValue() == 0) {
            sliderSFX.setValue(sfxValue);
            pbSFX.setProgress(sfxValue / 100);
        } else {
            sfxValue = sliderSFX.getValue();
            sliderSFX.setValue(0);
            pbSFX.setProgress(0);
        }
    }

    /**
     * This toggles the emote enabled button and updates a set variable accordingly.
     */
    public void setEmote() {
        if (emotes) {
            emotes = false;
            close.setOpacity(1);
        } else {
            emotes = true;
            close.setOpacity(0);
        }
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
