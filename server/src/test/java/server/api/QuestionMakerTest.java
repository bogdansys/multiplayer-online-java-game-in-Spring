package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuestionMakerTest {
    private TestActivityRepository repo;
    private QuestionMaker machine;

    @BeforeEach
    void setup() {
        repo = new TestActivityRepository();
        machine = new QuestionMaker(repo);
        repo.save(new Activity("1", "url", "do stuff", 2374, "wikipedia"));
        repo.save(new Activity("2", "url", "do stuff", 20, "wikipedia"));
        repo.save(new Activity("3", "url", "do stuff", 20374000, "wikipedia"));
    }

    @Test
    void getQuestion() {
        //The reason that I added this activity a bunch of times is that it speeds up the method and
        //so that the test doesn't take forever
        repo.save(new Activity("4", "url", "do stuff", 20374000, "wikipedia"));
        repo.save(new Activity("5", "url", "do stuff", 20374000, "wikipedia"));
        repo.save(new Activity("6", "url", "do stuff", 20374000, "wikipedia"));
        repo.save(new Activity("7", "url", "do stuff", 20374000, "wikipedia"));
        repo.save(new Activity("8", "url", "do stuff", 20374000, "wikipedia"));
        repo.save(new Activity("9", "url", "do stuff", 20374000, "wikipedia"));
        repo.save(new Activity("10", "url", "do stuff", 20374000, "wikipedia"));


        assertNotNull(QuestionMaker.getQuestion());
    }

    @Test
    void activityRanges() {
        assertEquals(QuestionMaker.activityRanges(), List.of(1, 1, 1));
        repo.save(new Activity("1", "url", "do stuff", 30, "wikipedia"));
        assertEquals(QuestionMaker.activityRanges(), List.of(1, 1, 2));
    }

    @Test
    void generateConsumesTheMost() {
        assertNotNull(QuestionMaker.generateConsumesTheMost(repo.findAll()));
    }

    @Test
    void generateInsteadOfXHowMuchY() {
        assertNotNull(QuestionMaker.generateInsteadOfXHowMuchY(repo.findAll()));
    }

    @Test
    void generateHowMuch() {
        assertNotNull(QuestionMaker.generateHowMuch(repo.findAll()));
    }
}