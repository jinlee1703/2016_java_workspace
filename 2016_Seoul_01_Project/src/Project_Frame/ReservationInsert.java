package Project_Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Project_DBInterface.DBInterface;

public class ReservationInsert {
	public ReservationInsert(String ds, String as) {
		
	}
}

class trainPanel extends JPanel implements ActionListener {
	public trainPanel(String ds, String as) throws Exception {
		ResultSet rs = DBInterface.Stmt.executeQuery("SELECT train.name,Date(train_service.Departure_time),TIME(train_service.Departure_time),date(addtime(train_service.Departure_time,schedule.Lead_time)),time(addtime(train_service.Departure_time,schedule.Lead_time)),format(schedule.Price,0) FROM train join schedule join train_service join station on train_service.Schedule_num=Schedule.id and train.id=train_num and schedule.Arrival_station=station.id where Departure_time>=now() and Departure_station=1 and Arrival_station=2;");
		ResultSet rss = DBInterface.Stmt.executeQuery("");
		
		JLabel label1 = new JLabel(rs.getString(1));
		JLabel label2 = new JLabel("<html>"+ds+"<br>"+rs.getString(2)+"<br>"+rs.getString(3)+"<html>");
		JLabel label3 = new JLabel("<html>"+as+"<br>"+rs.getString(4)+"<br>"+rs.getString(5)+"<html>");
		JLabel label4 = new JLabel();
		JLabel label5 = new JLabel();
		JButton btn = new JButton("예매하기");
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
