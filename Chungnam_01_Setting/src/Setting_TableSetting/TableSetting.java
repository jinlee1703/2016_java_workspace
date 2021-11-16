package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'Project000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `Project000`");
		}
		DBInterface.Stmt.execute("create database `Project000`");
		DBInterface.Stmt.execute("use `Project000`");
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `Project000`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//result
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`result` (  "
				+ "`id` INT(20) NOT NULL AUTO_INCREMENT,  "
				+ "`word` INT(20) NULL,  "
				+ "`java` INT(20) NULL,  "
				+ "`excel` INT(20) NULL,  "
				+ "`ppt` INT(20) NULL,  "
				+ "`studentid` INT(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//student
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`student` (  "
				+ "`id` INT(20) NOT NULL AUTO_INCREMENT,  "
				+ "`studentID` VARCHAR(20) NULL,  "
				+ "`name` VARCHAR(20) NULL,  "
				+ "`password` VARCHAR(20) NULL,  "
				+ "`sex` VARCHAR(20) NULL,  "
				+ "`type` VARCHAR(20) NULL,  "
				+ "`sin` VARCHAR(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//slimit
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`slimit` (  "
				+ "`id` INT(20) NOT NULL AUTO_INCREMENT,  "
				+ "`word` VARCHAR(20) NULL,  "
				+ "`java` VARCHAR(20) NULL,  "
				+ "`excel` VARCHAR(20) NULL,  "
				+ "`ppt` VARCHAR(20) NULL,  "
				+ "`studentid` INT(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//user
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`user` (  "
				+ "`id` INT(20) NOT NULL AUTO_INCREMENT,  "
				+ "`userID` VARCHAR(20) NULL,  "
				+ "`password` VARCHAR(20) NULL,  "
				+ "`name` VARCHAR(20) NULL,  "
				+ "`type` VARCHAR(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//study
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`study` (  "
				+ "`id` INT(20) NOT NULL AUTO_INCREMENT,  "
				+ "`word` TINYINT(1) NULL,  "
				+ "`java` TINYINT(1) NULL,  "
				+ "`excel` TINYINT(1) NULL,  "
				+ "`ppt` TINYINT(1) NULL,  "
				+ "`studentid` INT(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("Data/"+"result.txt");
		InputStream st2 = cl.getResourceAsStream("Data/"+"student.txt");
		InputStream st3 = cl.getResourceAsStream("Data/"+"slimit.txt");
		InputStream st4 = cl.getResourceAsStream("Data/"+"user.txt");
		InputStream st5 = cl.getResourceAsStream("Data/"+"study.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`student` (`studentID`, `name`, `password`, `sex`, `type`, `sin`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split(",");
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from project000.student where studentID='"+sp[4]+"'"); rs.next();
				DBInterface.Stmt.execute("INSERT INTO `project000`.`result` (`word`, `java`, `excel`, `ppt`, `studentid`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+rs.getString(1)+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split(",");
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from project000.student where studentID='"+sp[4]+"'"); rs.next();
				DBInterface.Stmt.execute("INSERT INTO `project000`.`slimit` (`word`, `java`, `excel`, `ppt`, `studentid`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+rs.getString(1)+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`user` (`userID`, `password`, `name`, `type`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st5, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				s = s.replace("TRUE", "1");
				s = s.replace("FALSE", "0");
				String[] sp = s.split(",");
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from project000.student where studentID='"+sp[4]+"'"); rs.next();
				DBInterface.Stmt.execute("INSERT INTO `project000`.`study` (`word`, `java`, `excel`, `ppt`, `studentid`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+rs.getString(1)+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
	
	public static void CreateRelation() throws Exception {
		//result>>student
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`result` "
				+ "ADD INDEX `resultkey_idx` (`studentid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`result` "
				+ "ADD CONSTRAINT `resultkey`  "
				+ "FOREIGN KEY (`studentid`)  "
				+ "REFERENCES `project000`.`student` (`id`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//slimit>>student
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`slimit` "
				+ "ADD INDEX `slimitkey_idx` (`studentid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`slimit` "
				+ "ADD CONSTRAINT `slimitkey`  "
				+ "FOREIGN KEY (`studentid`)  "
				+ "REFERENCES `project000`.`student` (`id`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//study>>student
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`study` "
				+ "ADD INDEX `studykey_idx` (`studentid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`study` "
				+ "ADD CONSTRAINT `studykey`  "
				+ "FOREIGN KEY (`studentid`)  "
				+ "REFERENCES `project000`.`student` (`id`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		System.out.println("Create Relation");
	}
}
