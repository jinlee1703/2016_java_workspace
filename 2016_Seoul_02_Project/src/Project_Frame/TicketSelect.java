package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Project_DBInterface.DBInterface;

public class TicketSelect extends JFrame implements ActionListener {
	String[] header = {"예매일","열차","출발역","출발시간","도착역","도착시간","좌석번호","가격"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn1 = new JButton("예매변경");
	JButton btn2 = new JButton("예매취소");
	JButton btn3 = new JButton("닫기");
	
	public TicketSelect() {
		setTitle("승차권확인");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel pp = new JPanel(new GridLayout(1, 3));
		pp.add(btn1); pp.add(btn2); pp.add(btn3);
		
		p.add(scroll, BorderLayout.CENTER);
		p.add(p1, BorderLayout.SOUTH);
		
		add(p);
		
		try {
			ResultSet rs =  DBInterface.Stmt.executeQuery("select * from user where uID='"+Login.id.getText()+"'");
			rs.next(); String u = rs.getString(1);
			rs = DBInterface.Stmt.executeQuery("SELECT distinct Reservation_date,train.name,Departure_station,date_format(Departure_time, '%x-%m-%d %h:%i'),Arrival_station,date_format(addtime(Departure_time, Lead_time), '%x-%m-%d %h:%i'),seat.name,format(price,'#,##0') FROM reservation join user join train_service join seat join schedule join station join train on Train_num=train.id and Train_service_num=train_service.id and Seat_num=seat.seat and Schedule_num=schedule.id and User_num='"+u+"';");
			// and Departure_time>=current_date()
			rs.last();
			String[][] newRow = new String[rs.getRow()][8];
			rs.beforeFirst();
			
			while(rs.next()) {
				for(int i=0; i<8; i++) {
					newRow[rs.getRow()-1][i] = rs.getString(i+1);
				}
			}
			
			for(int i=0; i<newRow.length; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[i][2]+"'");
				rs.next(); newRow[i][2]=rs.getString(2);
				
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[i][4]+"'");
				rs.next(); newRow[i][4]=rs.getString(2);
				
				model.addRow(newRow[i]);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter ts = new TableRowSorter(table.getModel());
		table.setRowSorter(ts);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			
		} else if(bt==btn1) {
			
		} else {
			
		}
	}
}
