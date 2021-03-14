package poo2.doodle.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import poo2.doodle.AlertUtil;
import poo2.doodle.App;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Teacher;

public class LoginController {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;

	public LoginController() {
		App.setResizable(true);
	}

	@FXML
	private void login() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();

		if (username.isBlank()) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Enter your username!");
			alert.showAndWait();
			return;
		}

		if (password.isBlank()) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Enter your password!");
			alert.showAndWait();
			return;
		}

		Teacher t = new TeacherDAO().get(username);
		
		
		if (t == null) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Wrong username or password");
			alert.showAndWait();
			return;
		}
		if (!t.getPassword().contentEquals(password)) {
			Alert alert = AlertUtil.info("Info!", "Info!", "Wrong username or password");
			alert.showAndWait();
			return;
		}
		App.setResizable(true);
		App.setRoot("teacherMain");
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@FXML
	private void newUser() {
		App.setRoot("teacherRegistration ");
	}

}
