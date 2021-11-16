package Setting_TableSetting;

import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'TrainDB'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `TrainDB`");
		}
		DBInterface.Stmt.execute("create database `TrainDB`");
	}
	
	public static void CreateGrants()throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `TrainDB`.* to user");
	}
	
	public static void CreateTables() throws Exception {
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`user` (  `id` INT NOT NULL AUTO_INCREMENT,  `uID` VARCHAR(10) NULL,  `uPW` VARCHAR(10) NULL,  `name` VARCHAR(10) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`reservation` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `User_num` INT(11) NULL,  `Train_service_num` INT(11) NULL,  `Seat_num` INT(11) NULL,  `Reservation_date` DATE NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`seat` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `Stateroom_num` INT(11) NULL,  `name` VARCHAR(10) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`stateroom` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `name` VARCHAR(10) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`train_service` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `Schedule_num` INT(11) NULL,  `Departure_time` DATETIME NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`schedule` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `Departure_station` INT(11) NULL,  `Arrival_station` INT(11) NULL,  `Train_num` INT(11) NULL,  `Lead_time` TIME NULL,  `Price` INT(11) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`train` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `name` VARCHAR(10) NULL,  PRIMARY KEY (`id`));");
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`station` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `name` VARCHAR(10) NULL,  PRIMARY KEY (`id`));");
	}
	
	public static void CreateData() throws Exception {
		DBInterface.Stmt.execute("use `traindb`");
		
		DataInsert("User", "user");
		DataInsert("Reservation", "reservation");
		DataInsert("Seat", "seat");
		DataInsert("Stateroom", "stateroom");
		DataInsert("Train_service", "train_service");
		DataInsert("Schedule", "schedule");
		DataInsert("Train", "train");
		DataInsert("Station", "station");
	}
	
	public static void DataInsert(String fn,String tn) throws Exception {
		String path = System.getProperty("user.dir")+"\\res\\DataFiles\\"+fn+".txt";
		path = path.replace('\\', '/');
		DBInterface.Stmt.execute("load data local infile '"+path+"' into table "+tn);
	}
}
