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
		System.out.println("A");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `Project000`.* to user");
		System.out.println("B");
	}
	
	public static void CreateTables() throws Exception {
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`member` (  `id` VARCHAR(11) NOT NULL,  `pw` VARCHAR(6) NULL,  `name` VARCHAR(45) NULL,  `phone_num` VARCHAR(13) NULL,  `address` VARCHAR(45) NULL,  `point` INT(11) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`product` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `name` VARCHAR(45) NULL,  `type` VARCHAR(2) NULL,  `price` INT(11) NULL,  `size` VARCHAR(45) NULL,  `amount` INT(11) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`purchase` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `recipient` VARCHAR(45) NULL,  `size` VARCHAR(45) NULL,  `amount` INT(11) NULL,  `cost` INT(11) NULL,  `address` VARCHAR(45) NULL,  `order_date` DATE NULL,  `delivery` TINYINT(11) NULL,  `product_id` INT(11) NULL,  `member_id` VARCHAR(11) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `project000`.`log` (  `id` VARCHAR(20) NULL);");
		System.out.println("C");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("datafiles/member.txt");
		InputStream st2 = cl.getResourceAsStream("datafiles/product.txt");
		InputStream st3 = cl.getResourceAsStream("datafiles/purchase.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `phone_num`, `address`, `point`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt++;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `type`, `price`, `size`, `amount`) VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt++;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=1) {
				String[] sp = s.split("\t");
				int tf;
				if(sp[6].equals("T")) {
					tf = 1;
				} else {
					tf = 0;
				}
				DBInterface.Stmt.execute("INSERT INTO `project000`.`purchase` (`recipient`, `size`, `amount`, `cost`, `address`, `order_date`, `delivery`, `product_id`, `member_id`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+tf+"', '"+sp[7]+"', '"+sp[8]+"');");
			}
			cnt++;
		}
		in.close();
		
		System.out.println("D");
	}
}
