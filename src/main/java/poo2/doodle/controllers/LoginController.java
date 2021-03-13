package poo2.doodle.controllers;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import poo2.doodle.App;
import poo2.doodle.entities.Teacher;

public class LoginController {

	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPasswd;
	
	private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	private Teacher professor = null;

	public LoginController() {
		App.setResizable(true);
	}

	@FXML
	private void login() {
		if (txtUsuario.getText().isBlank()) {
//			AlertaFX.alerta("Campo usuário em branco");
			return;
		}

		if (txtPasswd.getText().isBlank()) {
//			AlertaFX.alerta("Campo senha em branco");
			return;
		}

//		for (Teacher teacher : teachers) {
//			if (teacher.getLogin().contentEquals(txtUsuario.getText())) {
//				if (teacher.getPasswd().contentEquals(txtPasswd.getText())) {
//
//					if (teacher.getTipoPessoa().contentEquals("professor")) {
//						professor = (Teacher) teacher;
//
//						System.out.println("Encontrou");
//						// Ver com passar objeto ainda!!
//						App.setRoot("professorMain");
//						break;
//					}
//				}
//			}
//		}
		App.setRoot("professorMain");

	}

	@FXML
	private void sair() {
		Platform.exit();
	}

	@FXML
	private void novoUsuario() {
		App.setRoot("cadastroUsuario");
	}

}
