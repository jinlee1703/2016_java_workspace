package Setting_TableSetting;

import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet  rs = DBInterface.Stmt.executeQuery("show databases like 'project000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `project000`");
		}
		DBInterface.Stmt.execute("create database `project000`");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `project000`.* to user");
	}
	
	public static void CreateTables() throws Exception {
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`member` (  `id` VARCHAR(20) NOT NULL,  `pw` VARCHAR(20) NULL,  `name` VARCHAR(20) NULL,  `address` VARCHAR(30) NULL,  `email` VARCHAR(30) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`ledger` (  `memberid` VARCHAR(20) NULL,  `date` VARCHAR(20) NULL,  `division` VARCHAR(10) NULL,  `item` VARCHAR(10) NULL,  `pay` VARCHAR(10) NULL,  `amount` INT NULL,  `memo` VARCHAR(100) NULL);");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`log` (  `id` VARCHAR(20) NULL);");
		
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`ledger` ADD INDEX `key_idx` (`memberid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`ledger` ADD CONSTRAINT `key`  FOREIGN KEY (`memberid`)  REFERENCES `project000`.`member` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
	}
	
	public static void CreateData() throws Exception {
		DataInsert("member");
		DataInsert("ledger");
	}
	
	public static void DataInsert(String name) throws Exception {
		DBInterface.Stmt.execute("use `project000`");
		String path = System.getProperty("user.dir")+"\\지급자료\\"+name+".txt";
		path = path.replace('\\', '/');
		DBInterface.Stmt.execute("load data local infile '"+path+"' into table "+name+" ignore 1 lines");
	}
}
