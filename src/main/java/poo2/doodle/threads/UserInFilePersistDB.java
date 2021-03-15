package poo2.doodle.threads;

import poo2.doodle.db.UserInFile;

public class UserInFilePersistDB implements Runnable {

	@Override
	public void run() {
		UserInFile userInFile = new UserInFile();
		try {
			while (true) {
				userInFile.check();
				Thread.sleep(4200);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
