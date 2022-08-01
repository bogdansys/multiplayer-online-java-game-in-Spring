package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityRepository repo;

    /**
     * Constructor for activityController.
     *
     * @param repo the repository with the activities
     */
    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    /**
     * gets all the activities from the database.
     *
     * @return a list of all activities
     */
    @GetMapping(path = {""})
    public List<Activity> getAll() {
        return repo.findAll();
    }

    /**
     * adds a new activity to the database.
     *
     * @param activity an Activity object to be added to the database
     * @return a boolean that is true if the activity has been successfully added
     */
    @PostMapping(path = {""})
    public boolean add(@RequestBody Activity activity) {
        List<Activity> activities = repo.findAll();
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).id.equals(activity.id)) {
                return false;
            }
        }
        repo.save(activity);
        return true;
    }

    /**
     * updates an existing activity in the database.
     *
     * @param activity an Activity object to be updated in the database
     * @return a boolean that is true if the activity has been successfully updated
     */
    @PutMapping(path = {""})
    public boolean update(@RequestBody Activity activity) {
        List<Activity> activities = repo.findAll();
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).id.equals(activity.id)) {
                repo.delete(activities.get(i));
                repo.save(activity);
                return true;
            }
        }
        return false;
    }

    /**
     * deletes an activity from the database.
     *
     * @param id the id of the activity to be deleted from the database
     * @return a boolean that is true if the activity has been successfully deleted
     */
    @DeleteMapping(path = {""})
    public boolean delete(@RequestBody String id) {
        List<Activity> activities = repo.findAll();
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).id.equals(id)) {
                repo.delete(activities.get(i));
                return true;
            }
        }
        return false;
    }

    /**
     * adds a list of activities to the database.
     *
     * @param activity a list of activities to be added to the database
     * @return a String to test if this is working
     */
    @PostMapping(path = {"", "/list"})
    public String add(@RequestBody List<Activity> activity) {
        for (int i = 0; i < activity.size(); i++) {
            Activity saved = activity.get(i);
            repo.save(saved);
        }
        return "nice";
    }

    /**
     * Deletes an activity from the database and from the json file.
     *
     * @param activity the activity to be deleted
     * @return a boolean that is true if the activity has been successfully deleted
     */
    @PostMapping(path = {"/removeActivity"})
    public boolean remove(@RequestBody Activity activity) {
        List<Activity> list = repo.findAll();
        ObjectMapper mapper = new ObjectMapper();

        List<Activity> activityList;
        File activitiesBackUp;
        Activity[] activityArray;
        int counter = 0;
        if (list.remove(activity)) {
            try {
                try {
                    activitiesBackUp = new File("client/src/main/resources/ActivityBankImages/activity-bank/activities.json");
                    activityList = Arrays.asList(mapper.readValue(activitiesBackUp, Activity[].class));
                } catch (FileNotFoundException e) {
                    activitiesBackUp = new File("../client/src/main/resources/ActivityBankImages/activity-bank/activities.json");
                    activityList = Arrays.asList(mapper.readValue(activitiesBackUp, Activity[].class));
                }
                activityArray = new Activity[activityList.size() - 1];
                for (int i = 0; i < activityList.size(); i++) {
                    if (activityList.get(i).equals(activity)) ;
                    else {
                        activityArray[counter] = activityList.get(i);
                        counter++;
                    }
                }
                mapper.writeValue(activitiesBackUp, activityArray);
                repo.delete(activity);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Adds an activity to the database and to the json file.
     *
     * @param activity the activity to be added
     * @return a boolean that is true if the activity has been successfully added
     */
    @PostMapping(path = {"/addActivity"})
    public boolean addActivity(@RequestBody Activity activity) {
        List<Activity> list = repo.findAll();
        ObjectMapper mapper = new ObjectMapper();

        List<Activity> activityList;
        File activitiesBackUp;
        Activity[] activityArray;
        try {
            try {
                activitiesBackUp = new File("client/src/main/resources/ActivityBankImages/activity-bank/activities.json");
                activityList = Arrays.asList(mapper.readValue(activitiesBackUp, Activity[].class));
            } catch (FileNotFoundException e) {
                activitiesBackUp = new File("../client/src/main/resources/ActivityBankImages/activity-bank/activities.json");
                activityList = Arrays.asList(mapper.readValue(activitiesBackUp, Activity[].class));
            }
            activityArray = new Activity[activityList.size() + 1];
            for (int i = 0; i < activityList.size(); i++) {
                activityArray[i] = activityList.get(i);
            }
            activityArray[activityArray.length - 1] = activity;
            mapper.writeValue(activitiesBackUp, activityArray);
            repo.save(activity);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * initializes the database by adding all the activities from activities.json
     * this is done by using objectMappers to convert json into Activities and then post the entire list.
     *
     * @return a String to test if this is working
     */
    @PostMapping(path = {"", "/initializeDataBase"})
    public String initializeDataBase() {
        ObjectMapper mapper = new ObjectMapper();

        List<Activity> activityList;
        File activitiesBackUp;
        try {
            try {
                activitiesBackUp = new File("client/src/main/resources/ActivityBankImages/activity-bank/activities.json");
                activityList = Arrays.asList(mapper.readValue(activitiesBackUp, Activity[].class));
            } catch (FileNotFoundException e) {
                activitiesBackUp = new File("../client/src/main/resources/ActivityBankImages/activity-bank/activities.json");
                activityList = Arrays.asList(mapper.readValue(activitiesBackUp, Activity[].class));
            }
            for (Activity walker : activityList) {
                repo.save(walker);
            }
            return "database initialized";
        } catch (IOException e) {
            e.printStackTrace();
            return "read exception";
        }
    }
}
