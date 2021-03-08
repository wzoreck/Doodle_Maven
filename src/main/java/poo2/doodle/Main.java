package poo2.doodle;

import java.text.ParseException;
import poo2.doodle.bd.UtilBD;
import poo2.doodle.ihc.LoginFX;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) throws ParseException {

		UtilBD.initBD();
		Application.launch(LoginFX.class);
		UtilBD.fecharConexao();
	}
}