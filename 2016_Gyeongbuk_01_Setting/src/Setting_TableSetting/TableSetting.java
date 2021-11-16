package Setting_TableSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import Setting_DBInterface.DBInterface;

public class TableSetting {
	public static void CreateDB() throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("show databases like 'haksa_000'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop database `haksa_000`");
		}
		DBInterface.Stmt.execute("create database `haksa_000`");
		DBInterface.Stmt.execute("use `haksa_000`");
		
		System.out.println("Create DB");
	}
	
	public static void CreateGrants() throws Exception {
		DBInterface.Stmt.execute("use Mysql");
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where user='user'");
		if(rs.next()) {
			DBInterface.Stmt.execute("drop user `user`");
		}
		DBInterface.Stmt.execute("create user `user` identified by '1234'");
		DBInterface.Stmt.execute("grant select,insert,delete,update on `haksa_000`.* to user");
		
		System.out.println("Create Grants");
	}
	
	public static void CreateTables() throws Exception {
		//department
		DBInterface.Stmt.execute("CREATE TABLE `haksa_000`.`department` (  "
				+ "`deptno` INT(3) NOT NULL,  "
				+ "`deptname` VARCHAR(20) NULL,  "
				+ "`assistant` VARCHAR(20) NULL,  "
				+ "`tel` CHAR(13) NULL,  "
				+ "PRIMARY KEY (`deptno`));");
		
		//Student
		DBInterface.Stmt.execute("CREATE TABLE `haksa_000`.`student` (  "
				+ "`stdno` CHAR(10) NOT NULL,  "
				+ "`passwd` VARCHAR(10) NULL,  "
				+ "`stdname` VARCHAR(20) NULL,  "
				+ "`tel` CHAR(13) NULL,  "
				+ "`deptno` INT(3) NULL,  "
				+ "PRIMARY KEY (`stdno`));");
		
		//professor
		DBInterface.Stmt.execute("CREATE TABLE `haksa_000`.`professor` (  "
				+ "`profno` CHAR(4) NOT NULL,  "
				+ "`passwd` VARCHAR(10) NULL,  "
				+ "`profname` VARCHAR(20) NULL,  "
				+ "`tel` CHAR(13) NULL,  "
				+ "`major` VARCHAR(10) NULL,  "
				+ "`deptno` INT(3) NULL,  "
				+ "PRIMARY KEY (`profno`));");
		
		//subject
		DBInterface.Stmt.execute("CREATE TABLE `haksa_000`.`subject` (  "
				+ "`subjno` CHAR(4) NOT NULL,  "
				+ "`subjname` VARCHAR(20) NULL,  "
				+ "`credit` INT(1) NULL,  "
				+ "`profno` CHAR(4) NULL,  "
				+ "PRIMARY KEY (`subjno`));");
		
		//course
		DBInterface.Stmt.execute("CREATE TABLE `haksa_000`.`course` (  "
				+ "`course` INT(11) NOT NULL,  "
				+ "`stdno` CHAR(10) NULL,  "
				+ "`subjno` CHAR(4) NULL,  "
				+ "`score` INT(3) NULL,  "
				+ "PRIMARY KEY (`course`));");
		
		System.out.println("Create Tables");
	}
	
	public static void CreateKey() throws Exception {
		//Student1
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`student` "
				+ "ADD INDEX `stukey1_idx` (`deptno` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`student` "
				+ "ADD CONSTRAINT `stukey1`  "
				+ "FOREIGN KEY (`deptno`)  "
				+ "REFERENCES `haksa_000`.`department` (`deptno`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//professor1
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`professor` "
				+ "ADD INDEX `prokey1_idx` (`deptno` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`professor` "
				+ "ADD CONSTRAINT `prokey1`  "
				+ "FOREIGN KEY (`deptno`)  "
				+ "REFERENCES `haksa_000`.`department` (`deptno`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//subject1
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`subject` "
				+ "ADD INDEX `subkey1_idx` (`profno` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`subject` "
				+ "ADD CONSTRAINT `subkey1`  "
				+ "FOREIGN KEY (`profno`)  "
				+ "REFERENCES `haksa_000`.`professor` (`profno`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		
		//course1
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`course` "
				+ "ADD INDEX `coukey1_idx` (`stdno` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`course` "
				+ "ADD CONSTRAINT `coukey1`  "
				+ "FOREIGN KEY (`stdno`)  "
				+ "REFERENCES `haksa_000`.`student` (`stdno`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		//course2
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`course` "
				+ "ADD INDEX `coukey2_idx` (`subjno` ASC);");
		DBInterface.Stmt.execute("ALTER TABLE `haksa_000`.`course` "
				+ "ADD CONSTRAINT `coukey2`  "
				+ "FOREIGN KEY (`subjno`)  "
				+ "REFERENCES `haksa_000`.`subject` (`subjno`)  "
				+ "ON DELETE NO ACTION  "
				+ "ON UPDATE NO ACTION;");
		
		System.out.println("Create Key");
	}
	
	public void CreateData() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		InputStream st1 = cl.getResourceAsStream("Data/"+"DEPARTMENT.txt");
		InputStream st2 = cl.getResourceAsStream("Data/"+"STUDENT.txt");
		InputStream st3 = cl.getResourceAsStream("Data/"+"PROFESSOR.txt");
		InputStream st4 = cl.getResourceAsStream("Data/"+"SUBJECT.txt");
		InputStream st5 = cl.getResourceAsStream("Data/"+"COURSE.txt");
		
		String s;
		int cnt=1;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(st1, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `haksa_000`.`department` (`deptno`, `deptname`, `assistant`, `tel`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st2, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `haksa_000`.`student` (`stdno`, `passwd`, `stdname`, `tel`, `deptno`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st3, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `haksa_000`.`professor` (`profno`, `passwd`, `profname`, `tel`, `major`, `deptno`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"', '"+sp[4]+"', '"+sp[5]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st4, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `haksa_000`.`subject` (`subjno`, `subjname`, `credit`, `profno`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		cnt=1;
		
		in = new BufferedReader(new InputStreamReader(st5, "UTF8"));
		
		while((s=in.readLine()) !=null) {
			if(cnt!=0) {
				String[] sp = s.split(",");
				DBInterface.Stmt.execute("INSERT INTO `haksa_000`.`course` (`course`, `stdno`, `subjno`, `score`) "
						+ "VALUES ('"+sp[0]+"', '"+sp[1]+"', '"+sp[2]+"', '"+sp[3]+"');");
			}
			cnt+=1;
		}
		in.close();
		
		System.out.println("Create Data");
	}
}
