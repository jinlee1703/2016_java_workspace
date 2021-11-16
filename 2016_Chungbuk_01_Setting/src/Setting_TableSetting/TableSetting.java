package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'Cinema'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `Cinema`");
		}
		DBInterface.Stmt.execute("create database `Cinema`");
		DBInterface.Stmt.execute("use `Cinema`");
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `Cinema`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//customer
		DBInterface.Stmt.execute("CREATE TABLE `cinema`.`customer` (  "
				+ "`c_num` INT NOT NULL,  "
				+ "`id` VARCHAR(20) NULL,  "
				+ "`pw` VARCHAR(20) NULL,  "
				+ "`c_name` VARCHAR(20) NULL,  "
				+ "`tel` INT NULL,  "
				+ "PRIMARY KEY (`c_num`));");
		
		//snack
		DBInterface.Stmt.execute("CREATE TABLE `cinema`.`snack` (  "
				+ "`s_num` INT NOT NULL,  "
				+ "`s_type` VARCHAR(20) NULL,  "
				+ "`s_name` VARCHAR(20) NULL,  "
				+ "`s_price` INT NULL,  "
				+ "PRIMARY KEY (`s_num`));");
		
		//seat
		DBInterface.Stmt.execute("CREATE TABLE `cinema`.`seat` (  "
				+ "`t_num` INT NOT NULL,  "
				+ "`seat_num` VARCHAR(20) NOT NULL,  "
				+ "`isssue` INT NULL);");
		
		//movie
		DBInterface.Stmt.execute("CREATE TABLE `cinema`.`movie` (  "
				+ "`movie_num` INT NOT NULL,  "
				+ "`title` VARCHAR(20) NOT NULL,  "
				+ "PRIMARY KEY (`movie_num`));");
		
		 System.out.println("Create Table");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("Data/"+"customer.txt");
		InputStream st2 = cl.getResourceAsStream("Data/"+"snack.txt");
		InputStream st3 = cl.getResourceAsStream("Data/"+"seat.txt");
		InputStream st4 = cl.getResourceAsStream("Data/"+"movie.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `cinema`.`customer` (`c_num`, `id`, `pw`, `c_name`, `tel`) "
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
				DBInterface.Stmt.execute("INSERT INTO `cinema`.`snack` (`s_num`, `s_type`, `s_name`, `s_price`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `cinema`.`seat` (`t_num`, `seat_num`, `isssue`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `cinema`.`movie` (`movie_num`, `title`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
