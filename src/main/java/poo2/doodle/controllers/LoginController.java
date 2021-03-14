package poo2.doodle.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
//			AlertaFX.alerta("Campo usuário em branco");
			return;
		}

		if (password.isBlank()) {
//			AlertaFX.alerta("Campo senha em branco");
			return;
		}

		Teacher t = new TeacherDAO().get(username);
		
		
		if (t == null) {
			// Exception
			return;
		}
		if (!t.getPassword().contentEquals(password)) {
			// Exception
			return;
		}
		App.setResizable(true);
		App.setRoot("professorMain");
	}

	@FXML
	private void sair() {
		Platform.exit();
	}

	@FXML
	private void novoUsuario() {
		App.setRoot("teacherRegistration ");
	}

}
