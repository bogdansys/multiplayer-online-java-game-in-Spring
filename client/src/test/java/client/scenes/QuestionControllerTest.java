package client.scenes;

import client.Player;
import client.utils.QuestionUtils;
import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class QuestionControllerTest {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private QuestionController controller;
    private QuestionUtils questionUtils;
    private SettingsController settingsController;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.server = Mockito.mock(ServerUtils.class);
        this.questionUtils = Mockito.mock(QuestionUtils.class);
        this.settingsController = Mockito.mock(SettingsController.class);
        this.controller = new OpenQuestionController(mainCtrl, server, questionUtils, settingsController);
        controller.player = Mockito.mock(Player.class);
        controller.questionUtils.answered = false;
        controller.questionUtils.correctAnswerShown = false;
        controller.questionUtils.playersAnswered = 0;
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void setSingleplayerTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void setMultiplayerTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void showSettingsTest() {
        controller.showSettings();
        verify(mainCtrl, times(1)).showSettings();
    }

    @Test
    void updateQuestionTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void showCorrectAnswerTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void markAnswerTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void updateTimeTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void resetProgressBarTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void setPlayersAnsweredTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void useTimeReductionJokerTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void usePointsJokerTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void useAnswerJokerTest() {
        //Affects UI, so shouldn't be tested
    }
}