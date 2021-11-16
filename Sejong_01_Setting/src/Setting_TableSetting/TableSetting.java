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
				+ "`����ڵ�` VARCHAR(4) NOT NULL,  "
				+ "`�����` VARCHAR(10) NULL,  "
				+ "`����` VARCHAR(10) NULL,  "
				+ "`����` VARCHAR(1) NULL,  "
				+ "PRIMARY KEY (`����ڵ�`));");
		
		//customer
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`customer` (  "
				+ "`���ڵ�` VARCHAR(4) NOT NULL,  "
				+ "`����ȣ` VARCHAR(4) NULL,  "
				+ "`����` VARCHAR(10) NULL,  "
				+ "`���` VARCHAR(1) NULL,  "
				+ "PRIMARY KEY (`���ڵ�`));");
		
		//bankbook
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`bankbook` (  "
				+ "`���¹�ȣ` VARCHAR(7) NOT NULL,  "
				+ "`���ڵ�` VARCHAR(4) NULL,  "
				+ "`���°�����` DATE NULL,  "
				+ "`������` FLOAT NULL,  "
				+ "`�ܾ�` INT NULL,  "
				+ "PRIMARY KEY (`���¹�ȣ`));");
		
		//transaction
		DBInterface.Stmt.execute("CREATE TABLE `skill000`.`transaction` (  "
				+ "`����ڵ�` VARCHAR(4) NOT NULL,  "
				+ "`���¹�ȣ` VARCHAR(7) NULL,  "
				+ "`�ŷ�����` VARCHAR(1) NULL,  "
				+ "`�ŷ�����` DATE NULL,  "
				+ "`�ݾ�` INT NULL,  "
				+ "PRIMARY KEY (`����ڵ�`));");
		
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
				DBInterface.Stmt.execute("INSERT INTO `skill000`.`sawon` (`����ڵ�`, `�����`, `����`, `����`) "
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
				DBInterface.Stmt.execute("INSERT INTO `skill000`.`customer` (`���ڵ�`, `����ȣ`, `����`, `���`) "
						+ "VALUES ('"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
