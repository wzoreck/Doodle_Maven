package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.db.ContentDAO;
import poo2.doodle.entities.Content;

public class EditContentController {

	static Content content;

	@FXML
	TextField txtNewNote;

	public static void setContent(Content c) {
		content = c;
	}

	@FXML
	private void edit() {
		int newNote = Integer.valueOf(txtNewNote.getText());

		if (newNote < 0 || newNote > 10) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Please enter a score between 0 and 10");
			alert.showAndWait();
			return;
		}

		content.setNota(newNote);
		new ContentDAO().persist(content);
		back();
	}

	@FXML
	private void back() {
		Stage stage = (Stage) txtNewNote.getScene().getWindow();
		stage.close();
	}
}
