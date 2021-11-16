package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'Hair'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `Hair`");
		}
		DBInterface.Stmt.execute("create database `Hair`");
		DBInterface.Stmt.execute("use `Hair`");
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `Hair`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//customer
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`customer` (  "
				+ "`����ȣ` INT(11) NOT NULL,  "
				+ "`����` VARCHAR(20) NOT NULL,  "
				+ "`�������` DATE NULL,  "
				+ "`��������` DATE NULL,  "
				+ "PRIMARY KEY (`����ȣ`));");
		
		//event
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`event` (  "
				+ "`�̺�Ʈ��ȣ` INT(11) NOT NULL,  "
				+ "`�̺�Ʈ��` VARCHAR(20) NOT NULL,  "
				+ "`������` DOUBLE NULL,  "
				+ "PRIMARY KEY (`�̺�Ʈ��ȣ`));");
		
		//hair
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`hair` (  "
				+ "`����ȣ` INT(11) NOT NULL,  "
				+ "`����` VARCHAR(20) NOT NULL,  "
				+ "`����` INT(11) NULL,  "
				+ "PRIMARY KEY (`����ȣ`));");
		
		//business
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`business` (  "
				+ "`������ȣ` INT(11) NOT NULL,  "
				+ "`��������` DATE NULL,  "
				+ "`�湮�ð�` TIME NULL,  "
				+ "`����ȣ` INT(11) NOT NULL,  "
				+ "`����ȣ` INT(11) NOT NULL,  "
				+ "`�̺�Ʈ��ȣ` INT(11) NOT NULL,  "
				+ "PRIMARY KEY (`������ȣ`));");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("Data/"+"Customer.txt");
		InputStream st2 = cl.getResourceAsStream("Data/"+"Event.txt");
		InputStream st3 = cl.getResourceAsStream("Data/"+"Hair.txt");
		InputStream st4 = cl.getResourceAsStream("Data/"+"Business.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `hair`.`customer` (`����ȣ`, `����`, `�������`, `��������`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `hair`.`event` (`�̺�Ʈ��ȣ`, `�̺�Ʈ��`, `������`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `hair`.`hair` (`����ȣ`, `����`, `����`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `hair`.`business` (`������ȣ`, `��������`, `�湮�ð�`, `����ȣ`, `����ȣ`, `�̺�Ʈ��ȣ`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
