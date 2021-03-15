package poo2.doodle.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.App;
import poo2.doodle.db.ActivityDAO;
import poo2.doodle.db.ContentDAO;
import poo2.doodle.db.CourseDAO;
import poo2.doodle.entities.Activity;
import poo2.doodle.entities.Content;
import poo2.doodle.entities.Course;

public class CourseMainController implements Initializable {

	static Course course;

	@FXML
	private Button btnCreateNewContent;

	@FXML
	private Button btnEditContent;

	@FXML
	private Button btnDeleteContent;

	@FXML
	private Button btnReturnContent;

	@FXML
	private Button btnCreateNewActivitie;

	@FXML
	private Button btnEditActivitie;

	@FXML
	private Button btnDeleteActivitie;

	@FXML
	private Button btnReturnActivitie;

	@FXML
	private ListView<String> contentsList;

	@FXML
	private ListView<String> activitiesList;

	public static void setCourse(Course c) {
		course = c;
	}

	@FXML
	private void updateMyContents() {
		if (course == null)
			return;

		if (course.getContents() == null) {
			btnEditContent.setDisable(true);
			btnDeleteContent.setDisable(true);
			return;
		} else {
			contentsList.getSelectionModel().select(0);
			btnEditContent.setDisable(false);
			btnDeleteContent.setDisable(false);
		}

		List<String> courseContents = new ArrayList<>();
		for (Content content : course.getContents()) {
			courseContents.add(content.getName());
		}
		contentsList.setItems(FXCollections.observableArrayList(courseContents));
	}

	@FXML
	private void updateMyActivities() {
		if (course == null)
			return;

		if (course.getActivities() == null) {
			btnEditActivitie.setDisable(true);
			btnDeleteActivitie.setDisable(true);
			return;
		} else {
			activitiesList.getSelectionModel().select(0);
			btnEditActivitie.setDisable(false);
			btnDeleteActivitie.setDisable(false);
		}

		List<String> courseActivities = new ArrayList<>();
		for (Activity activity : course.getActivities()) {
			courseActivities.add(activity.getName());
		}
		activitiesList.setItems(FXCollections.observableArrayList(courseActivities));
	}

	@FXML
	private void removeContent() {
		String contentName = contentsList.getSelectionModel().getSelectedItem();
		Content content = new ContentDAO().get(contentName);
		course.getContents().remove(content);
		new CourseDAO().persist(course);
		new ContentDAO().remove(content);
		updateMyContents();
	}

	@FXML
	private void removeActivity() {
		String activityName = activitiesList.getSelectionModel().getSelectedItem();
		Activity activity = new ActivityDAO().get(activityName);
		course.getActivities().remove(activity);
		new CourseDAO().persist(course);
		new ActivityDAO().remove(activity);
		updateMyActivities();
	}

	@FXML
	private void createNewContent() {
		try {
			CreateContentController.setCourse(course);
			updateMyContents();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("createContent.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load createContent.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void createNewActivity() {
		try {
			CreateActivityController.setCourse(course);
			updateMyActivities();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("createActivity.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load createActivity.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void editContent() {
		try {
			String contentName = contentsList.getSelectionModel().getSelectedItem();
			Content content = new ContentDAO().get(contentName);
			updateMyContents();

			EditContentController.setContent(content);

			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editContent.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load editContent.fxml", e);
			alert.showAndWait();
		}
	}
	
	@FXML
	private void editActivity() {
		try {
			String activityName = activitiesList.getSelectionModel().getSelectedItem();
			Activity activity = new ActivityDAO().get(activityName);
			updateMyActivities();

			System.out.println("Activity: " + activity.getName());
			EditActivityController.setActivity(activity);

			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editActivity.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load editActivity.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void back() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("teacherMain.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) btnCreateNewContent.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load teacherMain.fxml", e);
			alert.showAndWait();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateMyContents();
		updateMyActivities();
	}

}
