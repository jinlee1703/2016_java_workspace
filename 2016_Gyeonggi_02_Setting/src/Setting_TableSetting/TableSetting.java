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
		DBInterface.Stmt.execute("grant select,insert,delete,update on `project000`.* to user");
		System.out.println("B");
	}
	
	public static void CreateTable() throws Exception {
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`member` (  `id` VARCHAR(20) NOT NULL,  `pw` VARCHAR(20) NULL,  `name` VARCHAR(20) NULL,  `address` VARCHAR(30) NULL,  `email` VARCHAR(30) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`ledger` (  `memberid` VARCHAR(20) NULL,  `date` VARCHAR(20) NULL,  `division` VARCHAR(10) NULL,  `item` VARCHAR(10) NULL,  `pay` VARCHAR(10) NULL,  `amount` INT NULL,  `memo` VARCHAR(100) NULL);");
		
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`ledger` ADD INDEX `key_idx` (`memberid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`ledger` ADD CONSTRAINT `key`  FOREIGN KEY (`memberid`)  REFERENCES `project000`.`member` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		System.out.println("C");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("member.txt");
		InputStream st2 = cl.getResourceAsStream("ledger.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "utf8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `address`, `email`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "utf8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				if(sp.length==7) {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`ledger` (`memberid`, `date`, `division`, `item`, `pay`, `amount`, `memo`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"');");
				} else {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`ledger` (`memberid`, `date`, `division`, `item`, `pay`, `amount`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
				}
				
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("D");
	}
}
