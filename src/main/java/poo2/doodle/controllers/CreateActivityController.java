package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.db.ActivityDAO;
import poo2.doodle.db.CourseDAO;
import poo2.doodle.entities.Activity;
import poo2.doodle.entities.Course;

public class CreateActivityController {

	static Course course;

	@FXML
	TextField txtActivityName;
	@FXML
	TextField txtActivityNote;

	public static void setCourse(Course c) {
		course = c;
	}

	@FXML
	private void register() {
		String activityName = txtActivityName.getText();
		int activityNote = Integer.valueOf(txtActivityNote.getText());

		if (activityName.isBlank()) {
			Alert alert = AlertUtil.info("Info", "Info", "Please enter a name");
			alert.showAndWait();
			return;
		}
		if (activityNote < 0 || activityNote > 10) {
			Alert alert = AlertUtil.info("Info", "Info", "Please enter a score between 0 and 10");
			alert.showAndWait();
			return;
		}

		Activity a = new ActivityDAO().get(activityName);

		if (a != null) {
			Alert alert = AlertUtil.info("Info", "This activity alredy exists", "");
			alert.showAndWait();
			return;
		}

		Activity activity = new Activity(activityName);
		activity.setNota(activityNote);
		new ActivityDAO().persist(activity);
		course.setActivitie(activity);
		new CourseDAO().persist(course);
		exit();
	}

	@FXML
	private void exit() {
		Stage stage = (Stage) txtActivityName.getScene().getWindow();
		stage.close();
	}
}
