package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class TicketSelect extends JFrame implements ActionListener {
	String[] haeder = {"예매일","열차","출발역","출발시간","도착역","도착시간","좌석번호","가격",""};
	DefaultTableModel model = new DefaultTableModel(haeder, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn1 = new JButton("예매변경");
	JButton btn2 = new JButton("예매취소");
	JButton btn3 = new JButton("닫기");
	
	public TicketSelect() {
		setTitle("승차권확인");
		setSize(700, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(scroll);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 1));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p2.add(btn1); p2.add(btn2); p2.add(btn3);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		table.removeColumn(table.getColumnModel().getColumn(8));
		
		TableSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(table.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "승차권 선택해주세요.");
				return;
			}
		} else if(bt==btn2) {
			if(table.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "승차권 선택해주세요.");
				return;
			}
			try {
				DBInterface.Stmt.execute("");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
	
	public void TableSetting() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("SELECT distinct Reservation_date,train.name,schedule.Departure_station,date(train_service.Departure_time),date_format(train_service.Departure_time, '%H:%i'),schedule.Arrival_station,date(addtime(train_service.Departure_time,Lead_time)),date_format(addtime(train_service.Departure_time,Lead_time), '%H:%i'),seat.name,format(price,'#,##0'),reservation.id FROM reservation join user join seat join train_service join schedule join station join train where User_num=user.id and Train_service_num=train_service.id and Seat_num=seat.id and Departure_station=station.id and Schedule_num=schedule.id and Departure_station=station.id and Train_num=train.id and user.uID='"+Login.id.getText()+"' and Departure_time>=current_time() order by Departure_time;");
			rs.last();
			
			String newRow[][] = new String[rs.getRow()][9];
			rs.beforeFirst();
			
			while(rs.next()) {
				newRow[rs.getRow()-1][0] = rs.getString(1);
				newRow[rs.getRow()-1][1] = rs.getString(2);
				newRow[rs.getRow()-1][2] = rs.getString(3);
				newRow[rs.getRow()-1][3] = rs.getString(4)+" "+rs.getString(5);
				newRow[rs.getRow()-1][4] = rs.getString(6);
				newRow[rs.getRow()-1][5] = rs.getString(7)+" "+rs.getString(8);
				newRow[rs.getRow()-1][6] = rs.getString(9);
				newRow[rs.getRow()-1][7] = rs.getString(10);
				newRow[rs.getRow()-1][8] = rs.getString(11);
			}
			
			for(int i=0; i<newRow.length; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[i][2]+"'");
				rs.next();
				newRow[i][2] = rs.getString(2);
			}
			
			for(int i=0; i<newRow.length; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[i][6]+"'");
				rs.next();
				newRow[i][6] = rs.getString(2);
				
				model.addRow(newRow[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
