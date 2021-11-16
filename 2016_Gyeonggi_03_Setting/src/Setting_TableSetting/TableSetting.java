package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'project000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `project000`");
		}
		DBInterface.Stmt.execute("create database `project000`");
		DBInterface.Stmt.execute("use `project000`");
		System.out.println("A");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `project000`.* to `user`");
		System.out.println("B");
	}
	
	public static void CreateTables() throws Exception {
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`member` (  `id` VARCHAR(20) NOT NULL,  `pw` VARCHAR(20) NULL,  `name` VARCHAR(20) NULL,  `address` VARCHAR(30) NULL,  `email` VARCHAR(30) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`ledger` (  `memberid` VARCHAR(20) NULL,  `date` VARCHAR(20) NULL,  `division` VARCHAR(10) NULL,  `item` VARCHAR(10) NULL,  `pay` VARCHAR(10) NULL,  `amount` INT NULL,  `memo` VARCHAR(100) NULL);");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`log` (  `id` VARCHAR(20) NULL);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`ledger` ADD INDEX `key_idx` (`memberid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`ledger` ADD CONSTRAINT `key`  FOREIGN KEY (`memberid`)  REFERENCES `project000`.`member` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		System.out.println("C");
	}
	
	public static void CreateData() throws Exception {
		DBInterface.Stmt.execute("use `project000`");
		DataInsert("member.txt", "member");
		DataInsert("ledger.txt", "ledger");
		System.out.println("C");
	}
	
	public static void DataInsert(String filename, String tablename) throws Exception {
		String path = System.getProperty("user.dir")+"\\res\\"+filename;
		path = path.replace('\\', '/');
		DBInterface.Stmt.execute("load data local infile '"+path+"' into table "+tablename+" ignore 1 lines");
	}
}
