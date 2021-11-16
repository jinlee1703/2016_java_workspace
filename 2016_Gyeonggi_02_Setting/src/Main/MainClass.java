package Main;

import Setting_DBInterface.DBInterface;
import Setting_TableSetting.TableSetting;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		TableSetting.CreateDB();
		TableSetting.CreateGrants();
		TableSetting.CreateTable();
		TableSetting ts = new TableSetting();
		ts.CreateData();
		
		System.out.println("All Success");
	}
}
