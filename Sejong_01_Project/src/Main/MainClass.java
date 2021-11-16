package Main;

import Project_DBInterface.DBInterface;
import Project_Frame.Login;
import Project_Frame.Main;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		//new Login();
		new Main();
	}
}
