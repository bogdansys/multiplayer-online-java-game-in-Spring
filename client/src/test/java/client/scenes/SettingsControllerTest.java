package client.scenes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SettingsControllerTest {

    private MainCtrl mainCtrl;
    private SettingsController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.controller = new SettingsController(mainCtrl);
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void showMainMenuTest() {
        controller.showMainMenu();
        verify(mainCtrl, times(1)).showMainMenu();
    }

    @Test
    void startSliderTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void stopSliderMusicTest() {
        //Affects sound, so shouldn't be tested
    }

    @Test
    void stopSliderSFXTest() {
        //Affects sound, so shouldn't be tested
    }

    @Test
    void musicOffTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void sfxOffTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void setEmoteTest() {
        //Affects UI, so shouldn't be tested
    }
}