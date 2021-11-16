package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'TestProject000'");
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
	
	public static void  CreateTables() throws Exception {
		//Employee
		DBInterface.Stmt.execute("CREATE TABLE `testproject000`.`employee` (  "
				+ "`id` INT NOT NULL AUTO_INCREMENT,  "
				+ "`pw` VARCHAR(20) NULL,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "`email` VARCHAR(20) NULL,  "
				+ "`phone` VARCHAR(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//Log
		DBInterface.Stmt.execute("CREATE TABLE `testproject000`.`log` (  "
				+ "`id` INT NOT NULL,  `time` DATE NOT NULL,  "
				+ "`content` VARCHAR(45) NULL,  "
				+ "PRIMARY KEY (`id`, `time`));");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st = cl.getResourceAsStream("Data/Employee.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st, "euc-kr"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `testproject000`.`employee` (`pw`, `name`, `email`, `phone`) VALUES ('"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
	
	public static void CreateRelation() throws Exception {
		//Log>>Employee
		DBInterface.Stmt.execute("ALTER TABLE `testproject000`.`log` ADD CONSTRAINT `key`");
		DBInterface.Stmt.execute("FOREIGN KEY (`id`)  REFERENCES `testproject000`.`employee` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		
		System.out.println("Create Relation");
	}
}
