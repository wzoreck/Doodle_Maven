package poo2.doodle.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Teacher;

public class TeacherRegistrationController {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;

	@FXML
	private void back() {
//		App.setRoot("login");
	}

	@FXML
	private void register() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();

		if (username.isBlank()) {
			// Alerta
			return;
		}
		if (password.isBlank()) {
			return;
		}
		Teacher t = new TeacherDAO().get(username);
		if (t != null) {
			// uauário já existe
			return;
		}
		new TeacherDAO().persist(new Teacher(username, password));
	}
}
