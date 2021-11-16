package Setting_TableSetting;

import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like `TestProject000`");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `TestProject000`");
		}
		DBInterface.Stmt.execute("create database `TestProject000`");
		DBInterface.Stmt.execute("use `TestProject000`");
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `TestProject000`.* to user");
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//Dept
		DBInterface.Stmt.execute("");
	}
}
