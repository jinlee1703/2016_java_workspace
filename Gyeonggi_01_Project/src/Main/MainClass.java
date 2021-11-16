package Main;

import Project_DBInterface.DBInterface;
import Project_Frame.Main;
import Project_Frame.OrderlistInsert;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		new Main();
	}
}
