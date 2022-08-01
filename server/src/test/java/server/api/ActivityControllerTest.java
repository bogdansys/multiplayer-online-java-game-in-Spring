package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActivityControllerTest {
    private TestActivityRepository repo;
    private ActivityController controller;

    @BeforeEach
    public void setup() {
        repo = new TestActivityRepository();
        controller = new ActivityController(repo);
    }

    @Test
    void testAdd() {
        controller.add(new Activity("1", "url", "do stuff", 20374, "wikipedia"));
        assertTrue(repo.count() == 1);
    }

    @Test
    void testAddList() {
        ArrayList<Activity> list = new ArrayList<>();
        list.add(new Activity("1", "url", "do stuff", 20374, "wikipedia"));
        list.add(new Activity("2", "url", "do more stuff", 133, "reddit"));
        controller.add(list);
        assertTrue(repo.count() == 2);
    }

    @Test
    void getAll() {
        repo.save(new Activity("1", "url", "do stuff", 20374, "wikipedia"));
        assertTrue(controller.getAll().size() != 0);
    }

    @Test
    void initializeDataBase() {
        controller.initializeDataBase();
        assertFalse(repo.count() == 0);
    }

    @Test
    void removeActivity() {
        //cant test
    }

    @Test
    void deleteActivity() {
        controller.add(new Activity("1", "url", "do stuff", 20374, "wikipedia"));
        controller.delete("1");
        assertTrue(repo.count() == 0);
    }

    @Test
    void addActivity() {
        //cant test
    }

    @Test
    void updateActivity() {
        controller.add(new Activity("1", "url", "do stuff", 20374, "wikipedia"));
        Activity a = new Activity("1", "b", "do a", 1, "wikipedia");
        controller.update(a);
        assertEquals(repo.findAll().get(0), a);
    }
}