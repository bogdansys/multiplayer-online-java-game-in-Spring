package client.scenes;


import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.ActivityEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminPanelController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final AddActivityController addActivityController;

    @FXML
    TableView<ActivityEntity> table;
    @FXML
    TableColumn<ActivityEntity, String> nameColumn;
    @FXML
    TableColumn<ActivityEntity, Long> WHColumn;

    @FXML
    private Label addedSuccessfully;

    /**
     * Constructor.
     * @param mainCtrl
     * @param server
     * @param addActivityController
     */
    @Inject
    public AdminPanelController(MainCtrl mainCtrl, ServerUtils server, AddActivityController addActivityController) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.addActivityController = addActivityController;
    }

    /**
     * Shows settings scene.
     */
    public void showSettings() {
        mainCtrl.showSettings();
    }

    /**
     * Opens the addActivity popup.
     */
    public void addActivity() {
        addActivityController.header.setText("New Activity");
        addActivityController.id.setText("ID");
        addActivityController.title.setText("Title");
        addActivityController.energy.setText("Consumption in wh");
        addActivityController.imagePath.setText("Image path");
        addActivityController.source.setText("Source");
        addActivityController.button.setText("Create");

        mainCtrl.showAddActivity();
    }

    /**
     * Shows a scene in which you can edit the activity that was selected.
     */
    public void editActivity() {
        List<Activity> list = server.getActivities();
        Activity activity = list.get(table.getSelectionModel().getFocusedIndex());

        addActivityController.header.setText("Edit Activity");
        addActivityController.id.setText(activity.id);
        addActivityController.title.setText(activity.title);
        addActivityController.energy.setText(String.valueOf(activity.consumption_in_wh));
        addActivityController.imagePath.setText(activity.image_path);
        addActivityController.source.setText(activity.source);
        addActivityController.button.setText("Save");

        mainCtrl.showAddActivity();
        server.deleteActivity(activity);
        showActivities();
    }

    /**
     * Deletes the selected activity.
     */
    public void deleteActivity() {
        int focusedIndex = table.getSelectionModel().getFocusedIndex();
        List<Activity> list = server.getActivities();
        server.deleteActivity(list.get(focusedIndex));
        showActivities();
    }

    /**
     * Show the message that the activity is successfully added.
     */
    public void showAddedSuccessfully(boolean show) {
        addedSuccessfully.setVisible(show);
    }

    /**
     * Updates the UI to show all the activities in the table.
     */
    public void showActivities() {
        List<Activity> list = server.getActivities();

        ObservableList<ActivityEntity> data = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            Activity a = list.get(i);
            data.add(new ActivityEntity(a.title, a.consumption_in_wh));
        }

        table.setItems(data);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ActivityEntity, String>("name"));
        WHColumn.setCellValueFactory(new PropertyValueFactory<ActivityEntity, Long>("WH"));
        table.getSelectionModel().setCellSelectionEnabled(true);
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
