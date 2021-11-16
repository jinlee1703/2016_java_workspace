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
				+ "`고객번호` INT(11) NOT NULL,  "
				+ "`고객명` VARCHAR(20) NOT NULL,  "
				+ "`생년월일` DATE NULL,  "
				+ "`가입일자` DATE NULL,  "
				+ "PRIMARY KEY (`고객번호`));");
		
		//event
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`event` (  "
				+ "`이벤트번호` INT(11) NOT NULL,  "
				+ "`이벤트명` VARCHAR(20) NOT NULL,  "
				+ "`할인율` DOUBLE NULL,  "
				+ "PRIMARY KEY (`이벤트번호`));");
		
		//hair
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`hair` (  "
				+ "`헤어번호` INT(11) NOT NULL,  "
				+ "`헤어명` VARCHAR(20) NOT NULL,  "
				+ "`가격` INT(11) NULL,  "
				+ "PRIMARY KEY (`헤어번호`));");
		
		//business
		DBInterface.Stmt.execute("CREATE TABLE `hair`.`business` (  "
				+ "`영업번호` INT(11) NOT NULL,  "
				+ "`영업일자` DATE NULL,  "
				+ "`방문시간` TIME NULL,  "
				+ "`고객번호` INT(11) NOT NULL,  "
				+ "`헤어번호` INT(11) NOT NULL,  "
				+ "`이벤트번호` INT(11) NOT NULL,  "
				+ "PRIMARY KEY (`영업번호`));");
		
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
				DBInterface.Stmt.execute("INSERT INTO `hair`.`customer` (`고객번호`, `고객명`, `생년월일`, `가입일자`) "
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
				DBInterface.Stmt.execute("INSERT INTO `hair`.`event` (`이벤트번호`, `이벤트명`, `할인율`) "
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
				DBInterface.Stmt.execute("INSERT INTO `hair`.`hair` (`헤어번호`, `헤어명`, `가격`) "
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
				DBInterface.Stmt.execute("INSERT INTO `hair`.`business` (`영업번호`, `영업일자`, `방문시간`, `고객번호`, `헤어번호`, `이벤트번호`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
