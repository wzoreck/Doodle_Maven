package poo2.doodle;

import javafx.application.Application;
import poo2.doodle.db.UtilDB;

public class Main {

	public static void main(String[] args) {
		UtilDB.initDB();
		Application.launch(App.class);
		UtilDB.closeConn();
	}
}