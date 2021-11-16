package Main;

import Setting_DBInterface.DBInterface;
import Setting_TableSetting.TableSetting;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		TableSetting.CreateDB();
		TableSetting.CreateGrants();
		TableSetting.CreateTables();
		TableSetting.CreateData();
		
		System.out.println("All Success");
	}
}
