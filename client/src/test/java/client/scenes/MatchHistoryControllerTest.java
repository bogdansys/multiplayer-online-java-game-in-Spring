package client.scenes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MatchHistoryControllerTest {

    private MainCtrl mainCtrl;
    private MatchHistoryController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.controller = new MatchHistoryController(mainCtrl);
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
}