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
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `project000`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//client
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`client` (  "
				+ "`id` INT NOT NULL,  "
				+ "`name` VARCHAR(45) NULL,  "
				+ "`birthday` DATE NULL,  "
				+ "`phone` VARCHAR(45) NULL,  "
				+ "`home` VARCHAR(100) NULL,  "
				+ "`email` VARCHAR(100) NULL,  "
				+ "`register_date` DATE NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//discount
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`discount` (  "
				+ "`name` VARCHAR(10) NOT NULL,  "
				+ "`discount_percent` DOUBLE NULL,  "
				+ "PRIMARY KEY (`name`));");
		
		//price
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`price` (  "
				+ "`id` INT NOT NULL,  `code_big` VARCHAR(45) NULL,  "
				+ "`code_mid` VARCHAR(45) NULL,  "
				+ "`name` VARCHAR(45) NULL,  "
				+ "`price` INT NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//sales
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`sales` (  "
				+ "`id` INT NOT NULL,  `salesDate` DATE NULL,  "
				+ "`salesTime` TIME NULL,  "
				+ "`client_id` INT NULL,  "
				+ "`client_name` VARCHAR(40) NULL,  "
				+ "`price_id` INT NULL,  "
				+ "`price_name` VARCHAR(45) NULL,  "
				+ "`discount_name` VARCHAR(45) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//orderlist
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`orderlist` (  "
				+ "`client_id` INT NULL,  "
				+ "`client_name` VARCHAR(45) NULL,  "
				+ "`sales_id` INT NULL,  "
				+ "`price_id` INT NULL);");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("Data/"+"client.txt");
		InputStream st2 = cl.getResourceAsStream("Data/"+"discount.txt");
		InputStream st3 = cl.getResourceAsStream("Data/"+"price.txt");
		InputStream st4 = cl.getResourceAsStream("Data/"+"sales.txt");
		InputStream st5 = cl.getResourceAsStream("Data/"+"orderlist.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "euc-kr"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`client` (`id`, `name`, `birthday`, `phone`, `home`, `email`, `register_date`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "euc-kr"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`discount` (`name`, `discount_percent`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "euc-kr"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`price` (`id`, `code_big`, `code_mid`, `name`, `price`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "euc-kr"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`sales` (`id`, `salesDate`, `salesTime`, `client_id`, `client_name`, `price_id`, `price_name`, `discount_name`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"', '"+sp[7]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st5, "euc-kr"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`orderlist` (`client_id`, `client_name`, `sales_id`, `price_id`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close(); 
		
		System.out.println("Create Data");
	}
}
