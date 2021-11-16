package Setting_TableSetting;

import java.io.File;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.StringTokenizer;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'TestProject000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `TestProject000`");
		}
		DBInterface.Stmt.execute("create database `TestProject000`");
		DBInterface.Stmt.execute("use `TestProject000`");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `TestProject000`.* to user");
	}
	
	public static void CreateTables() throws Exception {
		//product
		DBInterface.Stmt.execute("CREATE TABLE `testproject000`.`product` (  "
				+ "`code` CHAR(4) NOT NULL,  "
				+ "`name` VARCHAR(20) NULL,  "
				+ "`primecost` INT(11) NULL,  "
				+ "`sourcecost` INT(11) NULL,  "
				+ "PRIMARY KEY (`code`));");
		
		//salehistory
		DBInterface.Stmt.execute("CREATE TABLE `testproject000`.`salehistory` (  "
				+ "`idx` INT(11) NOT NULL,  "
				+ "`ecode` CHAR(4) NULL,  "
				+ "`bcode` CHAR(4) NULL,  "
				+ "`pcode` CHAR(4) NULL,  "
				+ "`salecnt` INT(11) NULL,  "
				+ "`salecost` INT(11) NULL,  "
				+ "`saledate` DATE NULL,  "
				+ "PRIMARY KEY (`idx`));");
		
		//emp
		DBInterface.Stmt.execute("CREATE TABLE `testproject000`.`emp` (  "
				+ "`code` CHAR(4) NOT NULL,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "`grade` CHAR(1) NULL,  "
				+ "PRIMARY KEY (`code`));");
		
		//business
		DBInterface.Stmt.execute("CREATE TABLE `testproject000`.`business` (  "
				+ "`code` CHAR(4) NOT NULL,  "
				+ "`name` VARCHAR(20) NULL,  "
				+ "`grade` CHAR(1) NULL,  "
				+ "PRIMARY KEY (`code`));");
	}
	
	public void tableDataImport(String fileName) throws Exception {
		String datapath = System.getProperty("user.dir") + "\\DataFiles\\" + fileName + ".txt";
		datapath = datapath.replace('\\', '/');
		//DBInterface.Stmt.execute("load data infile '" + datapath + "' into table `" + fileName + "` character set 'UTF8'");
		DBInterface.Stmt.execute("load data infile '" + datapath + "' into table `" + fileName + "` fields terminated by '\t' lines terminated by '\n'");
	}
}
