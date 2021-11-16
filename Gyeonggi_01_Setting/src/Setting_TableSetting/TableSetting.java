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
	}
	
	public static void CreateTables() throws Exception {
		//supplier
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`supplier` (  "
				+ "`id` INT(10) NOT NULL AUTO_INCREMENT,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//product
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`product` (  "
				+ "`id` INT(10) NOT NULL AUTO_INCREMENT,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "`supplierid` INT(10) NULL,  "
				+ "`amount` INT(15) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//orderlist
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`orderlist` (  "
				+ "`id` INT(10) NOT NULL AUTO_INCREMENT,  "
				+ "`orderdate` DATE NULL,  "
				+ "`memberid` VARCHAR(20) NULL,  "
				+ "`productid` INT(10) NULL,  "
				+ "`quantity` INT(10) NULL,  "
				+ "`pay` VARCHAR(10) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//worker
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`worker` (  "
				+ "`id` VARCHAR(20) NOT NULL,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "`workclassid` INT(10) NULL,  "
				+ "`score` INT(10) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//workclass
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`workclass` (  "
				+ "`id` INT(10) NOT NULL AUTO_INCREMENT,  "
				+ "`uid` INT(10) NULL,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//mileage
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`mileage` (  "
				+ "`id` VARCHAR(10) NOT NULL,  "
				+ "`name` VARCHAR(20) NULL,  "
				+ "`mileage` INT(20) NULL,  "
				+ "`memberid` VARCHAR(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//member
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`member` (  "
				+ "`id` VARCHAR(20) NOT NULL,  "
				+ "`name` VARCHAR(10) NULL,  "
				+ "`birthday` DATE NULL,  "
				+ "`address` VARCHAR(20) NULL,  "
				+ "`password` VARCHAR(20) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("DataFiles/"+"member.txt");
		InputStream st2 = cl.getResourceAsStream("DataFiles/"+"mileage.txt");
		InputStream st3 = cl.getResourceAsStream("DataFiles/"+"orderlist.txt");
		InputStream st4 = cl.getResourceAsStream("DataFiles/"+"product.txt");
		InputStream st5 = cl.getResourceAsStream("DataFiles/"+"supplier.txt");
		InputStream st6 = cl.getResourceAsStream("DataFiles/"+"workclass.txt");
		InputStream st7 = cl.getResourceAsStream("DataFiles/"+"worker.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `name`, `birthday`, `address`, `password`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`mileage` (`id`, `name`, `mileage`, `memberid`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`orderlist` (`orderdate`, `memberid`, `productid`, `quantity`, `pay`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `supplierid`, `amount`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st5, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`supplier` (`name`) "
						+ "VALUES ('"+sp[0]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st6, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`workclass` (`uid`, `name`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st7, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`worker` (`id`, `name`, `workclassid`, `score`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
	
	public static void CreateRelation() throws Exception {
		//product>>supplier
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`product` "
				+ "ADD INDEX `productkey_idx` (`supplierid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`product` "
				+ "ADD CONSTRAINT `productkey`  "
				+ "FOREIGN KEY (`supplierid`)  "
				+ "REFERENCES `project000`.`supplier` (`id`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//orderlist>>product
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`orderlist` "
				+ "ADD INDEX `orderlistkey1_idx` (`productid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`orderlist` "
				+ "ADD CONSTRAINT `orderlistkey1`  "
				+ "FOREIGN KEY (`productid`)  "
				+ "REFERENCES `project000`.`product` (`id`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
//		//orderlist>>member
//		DBInterface.Stmt.execute("ALTER TABLE `project000`.`orderlist` "
//				+ "ADD INDEX `orderlistkey2_idx` (`memberid` ASC);");
//		DBInterface.Stmt.execute("ALTER TABLE `project000`.`orderlist` "
//				+ "ADD CONSTRAINT `orderlistkey2`  "
//				+ "FOREIGN KEY (`memberid`)  "
//				+ "REFERENCES `project000`.`member` (`id`)  "
//				+ "ON DELETE NO ACTION  "
//				+ "ON UPDATE NO ACTION;");
//		
//		//mileage>>member
//		DBInterface.Stmt.execute("ALTER TABLE `project000`.`mileage` "
//				+ "ADD INDEX `mileagekey1_idx` (`memberid` ASC);");
//		DBInterface.Stmt.execute("ALTER TABLE `project000`.`mileage` "
//				+ "ADD CONSTRAINT `mileagekey1`  "
//				+ "FOREIGN KEY (`memberid`)  "
//				+ "REFERENCES `project000`.`member` (`id`)  "
//				+ "ON DELETE NO ACTION  "
//				+ "ON UPDATE NO ACTION;");
		
		//worker>>workclass
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`worker` "
				+ "ADD INDEX `workerkey1_idx` (`workclassid` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `project000`.`worker` "
				+ "ADD CONSTRAINT `workerkey1`  "
				+ "FOREIGN KEY (`workclassid`)  "
				+ "REFERENCES `project000`.`workclass` (`id`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		System.out.println("Create Releation");
	}
}
