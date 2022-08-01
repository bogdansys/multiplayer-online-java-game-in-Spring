package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaderBoardEntityTest {


    @Test
    void getRank() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        assertEquals(1, leaderBoardEntity.getRank());
    }

    @Test
    void setRank() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        leaderBoardEntity.setRank(10);
        assertEquals(10, leaderBoardEntity.getRank());
    }

    @Test
    void getName() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        assertEquals("john", leaderBoardEntity.getName());
    }

    @Test
    void setName() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        leaderBoardEntity.setName("asd");
        assertEquals("asd", leaderBoardEntity.getName());
    }

    @Test
    void setScore() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        leaderBoardEntity.setScore(123);
        assertEquals(123, leaderBoardEntity.getScore());
    }

    @Test
    void getPosition() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, 12, 9);
        assertEquals(9, leaderBoardEntity.getPosition());
    }

    @Test
    void setPosition() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, 12, 8);
        leaderBoardEntity.setPosition(20);
        assertEquals(20, leaderBoardEntity.getPosition());
    }

    @Test
    void testEqualsSameObject() {
        LeaderBoardEntity leaderBoardEntity1 = new LeaderBoardEntity(1, "john", 12);
        LeaderBoardEntity leaderBoardEntity2 = leaderBoardEntity1;
        assertEquals(leaderBoardEntity1, leaderBoardEntity2);
    }

    @Test
    void testEqualsDifferentObjectsEqual() {
        LeaderBoardEntity leaderBoardEntity1 = new LeaderBoardEntity(1, "john", 12);
        LeaderBoardEntity leaderBoardEntity2 = new LeaderBoardEntity(1, "john", 12);
        assertEquals(leaderBoardEntity1, leaderBoardEntity2);
    }

    @Test
    void testNotEqualsDifferentType() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        String string = "";
        assertNotEquals(leaderBoardEntity, string);
    }

    @Test
    void testNotEquals() {
        LeaderBoardEntity leaderBoardEntity1 = new LeaderBoardEntity(1, "john", 12);
        LeaderBoardEntity leaderBoardEntity2 = new LeaderBoardEntity(2, "john", 12);
        LeaderBoardEntity leaderBoardEntity3 = new LeaderBoardEntity(1, "james", 12);
        LeaderBoardEntity leaderBoardEntity4 = new LeaderBoardEntity(1, "john", 13);
        assertNotEquals(leaderBoardEntity1, leaderBoardEntity2);
        assertNotEquals(leaderBoardEntity1, leaderBoardEntity3);
        assertNotEquals(leaderBoardEntity1, leaderBoardEntity4);
    }

    @Test
    void testHashCode() {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity(1, "john", 12);
        assertNotNull(leaderBoardEntity.hashCode());
    }
}