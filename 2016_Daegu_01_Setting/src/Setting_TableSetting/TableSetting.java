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
		DBInterface.Stmt.execute("grant select, insert, delete, update on `World000`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//client
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`client` (  "
				+ "`code` INT(11) NOT NULL,  "
				+ "`id` VARCHAR(45) NOT NULL,  "
				+ "`pw` VARCHAR(20) NULL,  "
				+ "`name` VARCHAR(7) NULL,  "
				+ "`phnumber` VARCHAR(15) NULL,  "
				+ "`address` VARCHAR(70) NULL,  "
				+ "`RegiDate` DATE NULL,  "
				+ "PRIMARY KEY (`code`));");
		
		//ricecake
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`ricecake` (  "
				+ "`code` INT(11) NOT NULL,  "
				+ "`name` VARCHAR(45) NULL,  "
				+ "`storage` INT(11) NULL,  "
				+ "`salesvolume` INT(11) NULL,  "
				+ "PRIMARY KEY (`code`));");
		
		//sales
		DBInterface.Stmt.execute("CREATE TABLE `world000`.`sales` (  "
				+ "`code` INT(11) NOT NULL,  "
				+ "`salesdate` DATE NULL,  "
				+ "`fk_ricecakecode` INT(11) NULL,  "
				+ "`amount` INT(11) NULL,  "
				+ "`fk_clientcode` INT(11) NULL,  "
				+ "PRIMARY KEY (`code`));");
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("DataFiles/"+"client.txt");
		InputStream st2 = cl.getResourceAsStream("DataFiles/"+"ricecake.txt");
		InputStream st3 = cl.getResourceAsStream("DataFiles/"+"sales.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `world000`.`client` (`code`, `id`, `pw`, `name`, `phnumber`, `address`, `RegiDate`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `world000`.`ricecake` (`code`, `name`, `storage`, `salesvolume`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=1) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `world000`.`sales` (`code`, `salesdate`, `fk_ricecakecode`, `amount`, `fk_clientcode`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
	
	public static void CreateRelation() throws Exception {
		//client
		DBInterface.Stmt.execute("ALTER TABLE `world000`.`sales` "
				+ "ADD INDEX `saleskey1_idx` (`fk_clientcode` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `world000`.`sales` "
				+ "ADD CONSTRAINT `saleskey1`  "
				+ "FOREIGN KEY (`fk_clientcode`)  "
				+ "REFERENCES `world000`.`client` (`code`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//ricecake
		DBInterface.Stmt.execute("ALTER TABLE `world000`.`sales` "
				+ "ADD INDEX `saleskey2_idx` (`fk_ricecakecode` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `world000`.`sales` "
				+ "ADD CONSTRAINT `saleskey2`  "
				+ "FOREIGN KEY (`fk_ricecakecode`)  "
				+ "REFERENCES `world000`.`ricecake` (`code`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		System.out.println("Create Relation");
	}
}
