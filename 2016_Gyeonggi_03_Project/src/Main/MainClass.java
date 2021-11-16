package Main;

import Frame.Login;
import Project_DBInterface.DBInterface;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		new Login();
	}
}
