package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'MovieDB000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `MovieDB000`");
		}
		DBInterface.Stmt.execute("create database `MovieDB000`");
		DBInterface.Stmt.execute("use `MovieDB000`");
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `MovieDB000`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//objective
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`objective` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`Days` DATE NOT NULL,  "
				+ "`Cost` INT(11) NOT NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//user
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`user` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`uID` VARCHAR(10) NOT NULL,  "
				+ "`uPW` VARCHAR(15) NOT NULL,  "
				+ "`Name` VARCHAR(10) NOT NULL,  "
				+ "`Resident Registration Number` VARCHAR(14) NOT NULL,  "
				+ "`Phone number` VARCHAR(13) NOT NULL,  "
				+ "`Point` INT(6) NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//reservation
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`reservation` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`User_num` INT(11) NOT NULL,  "
				+ "`Screen_num` INT(11) NOT NULL,  "
				+ "`Seat` INT(11) NOT NULL,  "
				+ "`Reservation Time` DATETIME NOT NULL,  "
				+ "`Cost` INT(5) NOT NULL,  "
				+ "`Discount Content` VARCHAR(15) NULL,  "
				+ "`Discount Rate` DOUBLE NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//room seat
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`room seat` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`Room_num` INT(11) NOT NULL,  "
				+ "`Seat Name` VARCHAR(4) NOT NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//movie
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`movie` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`Title` VARCHAR(100) NOT NULL,  "
				+ "`Director` VARCHAR(10) NOT NULL,  "
				+ "`Cast` VARCHAR(50) NULL,  "
				+ "`Genre` VARCHAR(50) NOT NULL,  "
				+ "`Country` VARCHAR(15) NOT NULL,  "
				+ "`Release Date` DATE NOT NULL,  "
				+ "`Closing Date` DATE NOT NULL,  "
				+ "`Viewing time` INT(11) NOT NULL,  "
				+ "`Age Requirement` INT(3) NOT NULL,  "
				+ "`Cost` INT(5) NOT NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//screen
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`screen` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`Movie_num` INT(11) NOT NULL,  "
				+ "`Room_num` INT(11) NOT NULL,  "
				+ "`Viewing Time` DATETIME NOT NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		//room
		DBInterface.Stmt.execute("CREATE TABLE `moviedb000`.`room` (  "
				+ "`id` INT(11) NOT NULL,  "
				+ "`Room Name` VARCHAR(10) NOT NULL,  "
				+ "PRIMARY KEY (`id`));");
		
		
		System.out.println("Create Tables");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("DataFile/"+"Objective.txt");
		InputStream st2 = cl.getResourceAsStream("DataFile/"+"User.txt");
		InputStream st3 = cl.getResourceAsStream("DataFile/"+"Reservation.txt");
		InputStream st4 = cl.getResourceAsStream("DataFile/"+"Room Seat.txt");
		InputStream st5 = cl.getResourceAsStream("DataFile/"+"Movie.txt");
		InputStream st6 = cl.getResourceAsStream("DataFile/"+"Screen.txt");
		InputStream st7 = cl.getResourceAsStream("DataFile/"+"Room.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`objective` (`id`, `Days`, `Cost`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`user` (`id`, `uID`, `uPW`, `Name`, `Resident Registration Number`, `Phone number`, `Point`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`reservation` (`id`, `User_num`, `Screen_num`, `Seat`, `Reservation Time`, `Cost`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`room seat` (`id`, `Room_num`, `Seat Name`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st5, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`movie` (`id`, `Title`, `Director`, `Cast`, `Genre`, `Country`, `Release Date`, `Closing Date`, `Viewing time`, `Age Requirement`, `Cost`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"', '"+sp[6]+"', '"+sp[7]+"', '"+sp[8]+"', '"+sp[9]+"', '"+sp[10]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st6, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`screen` (`id`, `Movie_num`, `Room_num`, `Viewing Time`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st7, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt !=0) {
				String[] sp = s.split("\t");
				DBInterface.Stmt.execute("INSERT INTO `moviedb000`.`room` (`id`, `Room Name`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		
		System.out.println("Create Data");
	}
}
