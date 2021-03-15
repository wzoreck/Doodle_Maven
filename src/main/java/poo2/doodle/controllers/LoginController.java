package poo2.doodle.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poo2.doodle.AlertUtil;
import poo2.doodle.App;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Teacher;

public class LoginController implements Initializable {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;

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

		try {
			TeacherMainController.setUser(t);
			
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("teacherMain.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) txtUsername.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load teacherMain.fxml", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@FXML
	private void newUser() {
		try {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("teacherRegistration.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load teacherRegistration.fxml", e);
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Executa quando o controlador é criado

	}

}
