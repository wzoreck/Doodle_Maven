package poo2.doodle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {
	@FXML
	private Button btnOi;
	
	@FXML
	private void actionBtnOi() {
		if (btnOi.getText().contentEquals("Oi"))
			btnOi.setText("Tchau");
		else
			btnOi.setText("Oi");
	}
}
