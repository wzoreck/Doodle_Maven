package poo2.doodle.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.App;
import poo2.doodle.db.CourseDAO;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Course;
import poo2.doodle.entities.Teacher;

public class CreateNewCourseController {

	static Teacher teacher;

	@FXML
	TextField txtCourseName;
	@FXML
	TextField txtDescription;

	public static void setUser(Teacher t) {
		teacher = t;
	}

	@FXML
	private void register() {
		String courseName = txtCourseName.getText();
		String desescription = txtDescription.getText();

		if (courseName.isBlank()) {
			Alert alert = AlertUtil.info("Info", "Info", "Please enter a name");
			alert.showAndWait();
			return;
		}
		if (desescription.isBlank()) {
			Alert alert = AlertUtil.info("Info", "Info", "Please enter a description");
			alert.showAndWait();
			return;
		}

		Course c = new CourseDAO().get(courseName);
		
		if (c != null) {
			Alert alert = AlertUtil.info("Info", "This curse alredy exists", "");
			alert.showAndWait();
			return;
		}
		
		Course course = new Course(courseName, desescription);
		new CourseDAO().persist(course);
		teacher.setCourse(course);
		new TeacherDAO().persist(teacher);
		exit();
	}

	@FXML
	private void exit() {
		Stage stage = (Stage) txtCourseName.getScene().getWindow();
		stage.close();
	}

}
