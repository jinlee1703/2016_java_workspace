package Main;

import Project_DBInterface.DBInterface;
import Project_Frame.*;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		new Login();
	}
}
