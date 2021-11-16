package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Result extends JFrame {
	String[] header = {"열차","출발역","도착역","좌석현황","가격",""};
	JLabel[] hl = new JLabel[6];
	
	public Result() throws Exception {
		setTitle("조회결과");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p1 = new JPanel(new GridLayout(1, 6));
		p1.setBorder(new EmptyBorder(0, 0, 0, -15));
		for(int i=0; i<6; i++) {
			hl[i] = new JLabel(header[i]);
			hl[i].setOpaque(true);
			hl[i].setBackground(Color.gray);
			hl[i].setForeground(Color.white);
			hl[i].setPreferredSize(new Dimension(70, 30));
			p1.add(hl[i]);
		}
		hl[5].setOpaque(false);
		hl[5].setBackground(Color.white);
		
		String cn=null;
		if(Map.combo.getSelectedItem().toString().equals("전체")) {
			cn = "%";
		} else {
			cn = Map.combo.getSelectedItem().toString();
		}
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from station where name='"+Map.startStation.getText()+"'");
		rs.next(); String sn=rs.getString(1);
		
		rs = DBInterface.Stmt.executeQuery("select * from station where name='"+Map.endStation.getText()+"'");
		rs.next(); String en=rs.getString(1);
		
		rs = DBInterface.Stmt.executeQuery("select train.name,date(train_service.Departure_time),date_format(train_service.Departure_time, '%H:%i'),date(addtime(train_service.Departure_time,schedule.Lead_time)),date_format(addtime(train_service.Departure_time,schedule.Lead_time), '%H:%i'),format(price,'#,##0'),train_service.id from train_service join schedule join station join train on Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id where Departure_time>current_time() and Departure_station='"+sn+"' and Arrival_station='"+en+"' and train.name like '"+cn+"' order by Departure_time");
		rs.last();
		int length = rs.getRow();
		rs.beforeFirst();
		
		String[][] rowData = new String[length][6];
		while(rs.next()) {
			rowData[rs.getRow()-1][0] = rs.getString(1);
			rowData[rs.getRow()-1][1] = "<html>"+Map.startStation.getText()+"<br>"+rs.getString(2)+"<br>"+rs.getString(3);
			rowData[rs.getRow()-1][2] = "<html>"+Map.endStation.getText()+"<br>"+rs.getString(4)+"<br>"+rs.getString(5);
			rowData[rs.getRow()-1][3] = rs.getString(7);
			rowData[rs.getRow()-1][4] = rs.getString(6);
			rowData[rs.getRow()-1][5] = rs.getString(7);
		}
		
		for(int i=0; i<length; i++) {
			rs = DBInterface.Stmt.executeQuery("SELECT 48-count(*) FROM reservation where Train_service_num='"+rowData[i][3]+"';");
			rs.next();
			rowData[i][3] = rs.getInt(1)+"석 남음";
		}
		
		JPanel p2 = new JPanel(new GridLayout(length, 6));
		for(int i=0; i<length; i++) {
			JLabel[] l = new JLabel[5];
			
			for(int j=0; j<5; j++) {
				l[j] = new JLabel(rowData[i][j], JLabel.CENTER);
				p2.add(l[j]);
			}
			
			JPanel bp = new JPanel(new BorderLayout());
			bp.setBorder(new EmptyBorder(15, 0, 15, 0));
			JButton btn = new JButton("예매하기");
			int r = i;
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
					if(TicketSelect.st!=0) {
						new Seat(rowData[r][5], 2);
					} else {
						new Seat(rowData[r][5], 1);
					}
				}
			});
			bp.add(btn);
			p2.add(bp);
		}
		JScrollPane scroll = new JScrollPane(p2);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(p1, BorderLayout.NORTH); p.add(scroll, BorderLayout.CENTER);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
