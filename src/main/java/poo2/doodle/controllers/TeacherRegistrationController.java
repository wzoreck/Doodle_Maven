package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Teacher;

public class TeacherRegistrationController {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;

	@FXML
	private void back() {
		Stage stage = (Stage) txtEmail.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void register() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		String name = txtName.getText();
		String email = txtEmail.getText();

		if (username.isBlank()) {
			Alert alert = AlertUtil.info("Info", "Please inform a username", "");
			alert.showAndWait();
			return;
		}
		if (password.isBlank()) {
			Alert alert = AlertUtil.info("Info", "Please inform a password", "");
			alert.showAndWait();
			return;
		}

		Teacher t = new TeacherDAO().get(username);
		if (t != null) {
			Alert alert = AlertUtil.info("Info", "This user alredy exists", "");
			alert.showAndWait();
			return;
		}

		if (!name.isBlank() || !email.isBlank()) {
			if (name.isBlank()) {
				Alert alert = AlertUtil.info("Info", "Email is not blank, please insert a full name", "");
				alert.showAndWait();
				return;
			}
			if (email.isBlank()) {
				Alert alert = AlertUtil.info("Info", "Full name is not blank, please insert a email", "");
				alert.showAndWait();
				return;
			}
		}

		if (name.isBlank() && email.isBlank()) {
			new TeacherDAO().persist(new Teacher(username, password));
			back();
		} else {
			new TeacherDAO().persist(new Teacher(username, password, name, email));
			back();
		}

	}
}
