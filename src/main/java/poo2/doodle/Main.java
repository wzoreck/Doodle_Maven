package poo2.doodle;

import javafx.application.Application;
import poo2.doodle.db.UtilDB;
import poo2.doodle.threads.UserInFilePersistDB;

public class Main {

	public static void main(String[] args) {

		Thread loadFromFileToDB = new Thread(new UserInFilePersistDB());
		App.setLoadFromFileToDB(loadFromFileToDB);
		
		UtilDB.initDB();

//		new UserInFile().check();

		Application.launch(App.class);
		UtilDB.closeConn();
		
		loadFromFileToDB.interrupt();
	}
}