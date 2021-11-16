package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'World000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `World000`");
		}
		DBInterface.Stmt.execute("create database `World000`");
		DBInterface.Stmt.execute("use `World000`");
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `World000`.* to user");
		
		System.out.println("Create Grant");
	}
	
	public static void CreateTables() throws Exception {
		//admin
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`admin` (  "
				+ "`Name` VARCHAR(20) NULL,  "
				+ "`Jumin` CHAR(14) NOT NULL,  "
				+ "`Address` VARCHAR(100) NULL,  "
				+ "`School` VARCHAR(20) NULL,  "
				+ "`Phone` VARCHAR(20) NULL,  "
				+ "PRIMARY KEY (`Jumin`));");
		
		//report
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`report` (  "
				+ "`Jumin` CHAR(14) NOT NULL,  "
				+ "`Processing` INT NULL,  "
				+ "`Accounting` INT NULL,  "
				+ "`Web` INT NULL,  "
				+ "`Graphic` INT NULL,  "
				+ "`Electronic` INT NULL,  "
				+ "`TeacherCode` INT NULL,  "
				+ "`Number` INT NULL,  "
				+ "PRIMARY KEY (`Jumin`));");
		
		//appraisal
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`appraisal` (  "
				+ "`Number` INT NOT NULL,  "
				+ "`Onesemester` INT NULL,  "
				+ "`twosemester` INT NULL,  "
				+ "PRIMARY KEY (`Number`));");
		
		//teacher
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`protector` (  "
				+ "`TeacherCode` INT NOT NULL,  "
				+ "`ID` VARCHAR(20) NULL,  "
				+ "`PW` VARCHAR(20) NULL,  "
				+ "`Name` VARCHAR(20) NULL,  "
				+ "`Phone` VARCHAR(20) NULL,  "
				+ "`Subject` VARCHAR(100) NULL,  "
				+ "PRIMARY KEY (`TeacherCode`));");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("data/"+"student.txt");
		InputStream st2 = cl.getResourceAsStream("data/"+"report.txt");
		InputStream st3 = cl.getResourceAsStream("data/"+"appraisal.txt");
		InputStream st4 = cl.getResourceAsStream("data/"+"Teacher.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `world000`.`admin` (`Name`, `Jumin`, `Address`, `School`, `Phone`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `world000`.`report` (`Jumin`, `Processing`, `Accounting`, `Web`, `Graphic`, `Electronic`, `TeacherCode`, `Number`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"', '"+sp[7]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				if(sp.length==1) {
					DBInterface.Stmt.execute("INSERT INTO `world000`.`appraisal` (`Number`, `Onesemester`, `twosemester`) "
							+ "VALUES ('"+sp[0]+"', '"+0+"', '"+0+"');");
				} else {
					DBInterface.Stmt.execute("INSERT INTO `world000`.`appraisal` (`Number`, `Onesemester`, `twosemester`) "
							+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
				}
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `world000`.`protector` (`TeacherCode`, `ID`, `PW`, `Name`, `Phone`, `Subject`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
