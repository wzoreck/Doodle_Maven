package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.db.ContentDAO;
import poo2.doodle.db.CourseDAO;
import poo2.doodle.entities.Content;
import poo2.doodle.entities.Course;

public class CreateContentController {

	static Course course;

	@FXML
	TextField txtContentName;
	@FXML
	TextField txtContentNote;

	public static void setCourse(Course c) {
		course = c;
	}

	@FXML
	private void register() {
		String contentName = txtContentName.getText();
		int contentNote = Integer.valueOf(txtContentNote.getText());

		if (contentName.isBlank()) {
			Alert alert = AlertUtil.info("Info", "Info", "Please enter a name");
			alert.showAndWait();
			return;
		}
		if (contentNote < 0 || contentNote > 10) {
			Alert alert = AlertUtil.info("Info", "Info", "Please enter a score between 0 and 10");
			alert.showAndWait();
			return;
		}

		Content c = new ContentDAO().get(contentName);

		if (c != null) {
			Alert alert = AlertUtil.info("Info", "This content alredy exists", "");
			alert.showAndWait();
			return;
		}

		Content content = new Content(contentName);
		content.setNota(contentNote);
		new ContentDAO().persist(content);
		course.setContent(content);
		new CourseDAO().persist(course);
		exit();

	}

	@FXML
	private void exit() {
		Stage stage = (Stage) txtContentName.getScene().getWindow();
		stage.close();
	}
}
