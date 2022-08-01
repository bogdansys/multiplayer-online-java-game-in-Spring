package commons;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class EmoteTest {
    @Test
    void emptyConstructorTest() {
        Emote e = new Emote();
        assertNotNull(e);
    }

    @Test
    void constructorTest() {
        Emote e = new Emote("angry");
        assertNotNull(e);
    }

    @Test
    void testStartCounter() {
        Emote e = new Emote("angry");
        e.startCounter();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                assertEquals(400, e.counter);
            }
        }, 1500);
    }

    @Test
    void testEquals() {
        Emote e1 = new Emote("happy");
        Emote e2 = new Emote("happy");
        assertEquals(e1, e2);
    }

    @Test
    void testEqualsSame() {
        Emote e1 = new Emote("happy");
        Emote e2 = e1;
        assertEquals(e1, e2);
    }

    @Test
    void testNotEquals() {
        Emote e1 = new Emote("happy");
        Emote e2 = new Emote("angry");
        assertNotEquals(e1, e2);
        assertNotEquals(e1, null);
    }

    @Test
    void testHashCode() {
        Emote e1 = new Emote("happy");
        Emote e2 = new Emote("sad");
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void testToString() {
        String e1 = new Emote("happy").toString();
        assertTrue(e1.contains("type"));
    }
}