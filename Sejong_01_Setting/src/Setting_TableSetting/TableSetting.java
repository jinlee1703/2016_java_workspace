package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'Skill000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `Skill000`");
		}
		DBInterface.Stmt.execute("create database `Skill000`");
		DBInterface.Stmt.execute("use `Skill000`");
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `Skill000`.* to user");
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//sawon
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`sawon` (  "
				+ "`사원코드` VARCHAR(4) NOT NULL,  "
				+ "`사원명` VARCHAR(10) NULL,  "
				+ "`직급` VARCHAR(10) NULL,  "
				+ "`권한` VARCHAR(1) NULL,  "
				+ "PRIMARY KEY (`사원코드`));");
		
		//customer
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`customer` (  "
				+ "`고객코드` VARCHAR(4) NOT NULL,  "
				+ "`고객암호` VARCHAR(4) NULL,  "
				+ "`고객명` VARCHAR(10) NULL,  "
				+ "`등급` VARCHAR(1) NULL,  "
				+ "PRIMARY KEY (`고객코드`));");
		
		//bankbook
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`bankbook` (  "
				+ "`계좌번호` VARCHAR(7) NOT NULL,  "
				+ "`고객코드` VARCHAR(4) NULL,  "
				+ "`계좌개설일` DATE NULL,  "
				+ "`이자율` FLOAT NULL,  "
				+ "`잔액` INT NULL,  "
				+ "PRIMARY KEY (`계좌번호`));");
		
		//transaction
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`transaction` (  "
				+ "`사원코드` VARCHAR(4) NOT NULL,  "
				+ "`계좌번호` VARCHAR(7) NULL,  "
				+ "`거래종류` VARCHAR(1) NULL,  "
				+ "`거래일자` DATE NULL,  "
				+ "`금액` INT NULL,  "
				+ "PRIMARY KEY (`사원코드`));");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("Data/"+"sawon.txt");
		InputStream st2 = cl.getResourceAsStream("Data/"+"customer.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `skill000`.`sawon` (`사원코드`, `사원명`, `직급`, `권한`) "
						+ "VALUES ('"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `skill000`.`customer` (`고객코드`, `고객암호`, `고객명`, `등급`) "
						+ "VALUES ('"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
