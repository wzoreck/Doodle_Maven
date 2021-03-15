package poo2.doodle.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import poo2.doodle.entities.Teacher;

public class UserInFile {

	public void check() {
		String fileLocation = "./local-users.json";
		List<String> fileLines = new ArrayList<>();
		try {
			File file = new File(fileLocation);
			if (file.exists()) {
				Scanner scanner = new Scanner(file);
				while (scanner.hasNextLine())
					fileLines.add(scanner.nextLine());
				scanner.close();
			} else {
				System.err.println("File \"" + fileLocation + "\" is misssing");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error while opening file \"" + fileLocation + "\".");
		}

		// Processamento
		List<Teacher> teacherList = UtilDB.consumeAPI(fileLines);
		for (Teacher t : teacherList) {
			Teacher existingTeacher = new TeacherDAO().get(t.getUsername());
			if (existingTeacher != null) {
				if (!t.getUsername().contentEquals(existingTeacher.getUsername())) {
					new TeacherDAO().persist(t);
				}
			} else {
				new TeacherDAO().persist(t);
			}
		}
	}
}
