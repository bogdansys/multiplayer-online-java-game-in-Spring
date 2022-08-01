package client;

import client.scenes.ClosedQuestionController;
import client.scenes.MainCtrl;
import client.scenes.QuestionController;
import client.utils.ServerUtils;
import commons.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PlayerTest {
    private MainCtrl mainCtrl;
    private QuestionController questionController;
    private ServerUtils server;
    private Player player;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.questionController = Mockito.mock(ClosedQuestionController.class);
        this.server = Mockito.mock(ServerUtils.class);
        this.player = new Player("playerName", mainCtrl, questionController, server);
        player.setParams();
        questionController.setSingleplayer();
    }

    @Test
    public void reduceTimeTest() {
        player.reduceTime(new Tuple("player2", 5));
        assertEquals(15, player.timeLeft);
        verify(questionController, times(1)).resetProgressBar(15000, 20000);
    }

    @Test
    void defaultConstructorTest() {
        Player player = new Player();
        assertNotNull(player);
    }

    @Test
    void constructorTest() {
        assertNotNull(player);
    }

    @Test
    void reduceTimeTestExcessUse() {
        for (int i = 0; i < 5; i++) {
            player.reduceTime(new Tuple("player2", 5));
        }
        assertEquals(5, player.timeLeft);
    }

    @Test
    void resetTimeTest() {
        player.reduceTime(new Tuple("player2", 5));
        player.reduceTime(new Tuple("player2", 5));
        player.resetTime();
        assertEquals(20, player.timeLeft);
    }

    @Test
    void getDoublePointsTest() {
        assertFalse(player.getDoublePoints());
    }

    @Test
    void useDoublePointsTest() {
        player.useDoublePoints();
        assertTrue(player.getDoublePoints());
    }

    @Test
    void getEliminateAnswer() {
        assertFalse(player.isEliminateAnswerUsed());
    }

    @Test
    void usedEliminateAnswer() {
        player.useEliminateAnswer();
        assertTrue(player.isEliminateAnswerUsed());
    }

    @Test
    void incrementQuestionNumberTest() {
        assertEquals(0, player.questionNumber);
        player.incrementQuestionNumber();
        assertEquals(1, player.questionNumber);
    }

    @Test
    void disconnectTest() {
        player.lobbyID = 1;
        player.disconnect();
        verify(server, times(1)).disconnect(eq(new Tuple(player.name, player.lobbyID)));
    }

    @Test
    void startRoundTest() {
        player.score = 2000;
        player.questionNumber = 15;
        player.startRound();
        assertEquals(0, player.score);
        assertEquals(0, player.questionNumber);
        verify(questionController, times(1)).registerForPlayerAnsweredMessages();
        verify(questionController, times(1)).registerForPlayerDisconnectedMessages();
        verify(questionController, times(1)).nextQuestion();
    }

}