package Main;

import Setting_DBInterface.DBInterface;
import Setting_TableSetting.TableSetting;

public class MainClass {
	public static void main(String[] args) throws Exception {
		DBInterface.Init();
		TableSetting.CreateDB();
		TableSetting.CreateGrants();
		TableSetting.CreateTables();
		TableSetting ts = new TableSetting();
		ts.CreateData();
		TableSetting.CreateRelation();
		
		System.out.println("All Success");
	}
}
