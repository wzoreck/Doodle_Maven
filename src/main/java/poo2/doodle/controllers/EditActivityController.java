package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.db.ActivityDAO;
import poo2.doodle.entities.Activity;

public class EditActivityController {

	static Activity activity;

	@FXML
	TextField txtNewNote;

	public static void setActivity(Activity a) {
		activity = a;
	}

	@FXML
	private void edit() {
		int newNote = Integer.valueOf(txtNewNote.getText());

		if (newNote < 0 || newNote > 10) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Please enter a score between 0 and 10");
			alert.showAndWait();
			return;
		}

		activity.setNota(newNote);
		new ActivityDAO().persist(activity);
		back();
	}

	@FXML
	private void back() {
		Stage stage = (Stage) txtNewNote.getScene().getWindow();
		stage.close();
	}
}
