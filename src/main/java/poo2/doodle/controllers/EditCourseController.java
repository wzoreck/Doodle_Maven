package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.db.CourseDAO;
import poo2.doodle.entities.Course;

public class EditCourseController {

	static Course course;

	@FXML
	private TextField txtNewDescription;

	public static void setCourse(Course c) {
		course = c;
	}

	@FXML
	private void edit() {
		String description = txtNewDescription.getText();

		if (description.isBlank()) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Enter with new description!");
			alert.showAndWait();
			return;
		}

		course.setDescription(description);
		new CourseDAO().persist(course);
		back();
	}

	@FXML
	private void back() {
		Stage stage = (Stage) txtNewDescription.getScene().getWindow();
		stage.close();
	}
}
