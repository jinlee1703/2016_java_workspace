package Setting_TableSetting;

import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'traindb'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `traindb`");
		}
		DBInterface.Stmt.execute("create database `traindb`");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `traindb`.* to user");
	}
	
	public static void CreateTables() throws Exception {
		DBInterface.Stmt.execute("CREATE TABLE `traindb`.`user` (  `id` INT(11) NOT NULL AUTO_INCREMENT,  `uID` VARCHAR(10) NULL,  `uPW` VARCHAR(10) NULL,  `name` VARCHAR(10) NULL,  PRIMARY KEY (`id`));");
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
	
	public static  void DataInsert(String fn, String tn) throws Exception {
		String path = System.getProperty("user.dir")+"\\지급자료\\DataFiles\\"+fn+".txt";
		path = path.replace('\\', '/');
		DBInterface.Stmt.execute("load data local infile '"+path+"' into table "+tn);
	}
	
	public static void CreateKey() throws Exception {
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`reservation` ADD INDEX `reskey1_idx` (`User_num` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`reservation` ADD CONSTRAINT `reskey1`  FOREIGN KEY (`User_num`)  REFERENCES `traindb`.`user` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`reservation` ADD INDEX `reskey2_idx` (`Train_service_num` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`reservation` ADD CONSTRAINT `reskey2`  FOREIGN KEY (`Train_service_num`)  REFERENCES `traindb`.`train_service` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`reservation` ADD INDEX `reskey3_idx` (`Seat_num` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`reservation` ADD CONSTRAINT `reskey3`  FOREIGN KEY (`Seat_num`)  REFERENCES `traindb`.`seat` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`seat` ADD INDEX `seatkey1_idx` (`Stateroom_num` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`seat` ADD CONSTRAINT `seatkey1`  FOREIGN KEY (`Stateroom_num`)  REFERENCES `traindb`.`stateroom` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`train_service` ADD INDEX `tskey1_idx` (`Schedule_num` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`train_service` ADD CONSTRAINT `tskey1`  FOREIGN KEY (`Schedule_num`)  REFERENCES `traindb`.`schedule` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`schedule` ADD INDEX `sckey1_idx` (`Train_num` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`schedule` ADD CONSTRAINT `sckey1`  FOREIGN KEY (`Train_num`)  REFERENCES `traindb`.`train` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`schedule` ADD INDEX `sckey2_idx` (`Departure_station` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`schedule` ADD CONSTRAINT `sckey2`  FOREIGN KEY (`Departure_station`) REFERENCES `traindb`.`station` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`schedule` ADD INDEX `sckey3_idx` (`Arrival_station` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `traindb`.`schedule` ADD CONSTRAINT `sckey3`  FOREIGN KEY (`Arrival_station`)  REFERENCES `traindb`.`station` (`id`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;");
		
	}
}
