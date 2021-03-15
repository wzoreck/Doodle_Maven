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
import poo2.doodle.db.CourseDAO;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Course;
import poo2.doodle.entities.Teacher;

public class TeacherMainController implements Initializable {

	private static Teacher teacher;

	@FXML
	private Button btnCreateNew;

	@FXML
	private Button btnDelete;
	@FXML
	private Button btnAccess;
	@FXML
	private Button btnEdit;

	@FXML
	private ListView<String> teacherCoursesList;

	public static void setUser(Teacher t) {
		teacher = t;
	}

	@FXML
	private void exit() {
		teacher = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) btnDelete.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load login.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void updateMyCourses() {
		if (teacher == null)
			return;

		if (teacher.getCourses() == null) {
			btnDelete.setDisable(true);
			btnAccess.setDisable(true);
			btnEdit.setDisable(true);
			return;
		} else {
			teacherCoursesList.getSelectionModel().select(0);
			btnDelete.setDisable(false);
			btnAccess.setDisable(false);
			btnEdit.setDisable(false);
		}

		List<String> teacherCourses = new ArrayList<>();
		for (Course course : teacher.getCourses()) {
			teacherCourses.add(course.getName());
		}
		teacherCoursesList.setItems(FXCollections.observableArrayList(teacherCourses));
	}

	@FXML
	private void remove() {
		String courseName = teacherCoursesList.getSelectionModel().getSelectedItem();
		Course course = new CourseDAO().get(courseName);
		teacher.getCourses().remove(course);
		new TeacherDAO().persist(teacher);
		updateMyCourses();
	}
	
	@FXML
	private void access() {
		String courseName = teacherCoursesList.getSelectionModel().getSelectedItem();
		Course course = new CourseDAO().get(courseName);
		CourseMainController.setCourse(course);
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("courseMain.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) btnDelete.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load courseMain.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void createNewCourse() {
		try {
			CreateNewCourseController.setUser(teacher);
			updateMyCourses();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("createNewCourse.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load createNewCourse.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void editCourse() {
		try {
			String courseName = teacherCoursesList.getSelectionModel().getSelectedItem();
			Course course = new CourseDAO().get(courseName);
			updateMyCourses();
			
			EditCourseController.setCourse(course);
			
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editCourse.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load editCourse.fxml", e);
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateMyCourses();
	}
}
