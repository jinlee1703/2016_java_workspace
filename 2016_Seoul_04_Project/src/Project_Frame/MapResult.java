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
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class MapResult extends JFrame {
	String[] ln = {"열차","출발역","도착역","좌석현황","가격"};
	JLabel[] hl = new JLabel[6];
	JLabel[] l;
	
	public MapResult() throws Exception {
		setTitle("조회결과");
		setSize(1000, 1000);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();
		
		JPanel p1 = new JPanel(new GridLayout(1, 6));
		for(int i=0; i<6; i++) {
			hl[i] = new JLabel(ln[i]);
			hl[i].setBackground(Color.gray);
			hl[i].setForeground(Color.white);
			hl[i].setPreferredSize(new Dimension(100, 30));
		}
		
		String a,b,c;
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from station where name='"+Map.ss.getText()+"'");
		rs.next(); a=rs.getString(1);
		
		rs = DBInterface.Stmt.executeQuery("select * from station where name='"+Map.es.getText()+"'");
		rs.next(); b=rs.getString(1);
		
		if(Map.combo.getSelectedItem().toString().equals("전체")) {
			c = "%";
		} else {
			c = Map.combo.getSelectedItem().toString();
		}
		
		rs = DBInterface.Stmt.executeQuery("SELECT train.name,date(train_service.Departure_time),date_format(train_service.Departure_time, '%H:%i'),date(addtime(train_service.Departure_time,Lead_time)),date_format(addtime(train_service.Departure_time,Lead_time), '%H:%i'),format(price,'#,##0'),train_service.id FROM train_service join schedule join station join train where Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id and Departure_time>=current_time() and Departure_station='"+a+"' and Arrival_station='"+b+"' and train.name like '"+c+"' order by Departure_time;");
		rs.last();
		int length = rs.getRow();
		
		rs.beforeFirst();
		
		String[][] rowData = new String[length][6];
		while(rs.next()) {
			rowData[rs.getRow()-1][0] = rs.getString(1);
			rowData[rs.getRow()-1][1] = "<html>"+Map.ss.getText()+rs.getString(2)+"<br>"+rs.getString(3)+"</html>";
			rowData[rs.getRow()-1][2] = "<html>"+Map.es.getText()+rs.getString(4)+"<br>"+rs.getString(5)+"</html>";
			rowData[rs.getRow()-1][3] = rs.getString(7);
			rowData[rs.getRow()-1][4] = rs.getString(6);
			rowData[rs.getRow()-1][5] = rs.getString(7);
		}
		
		for(int i=0; i<length; i++) {
			rs = DBInterface.Stmt.executeQuery("select count(*) from reservation where Train_service_num='"+rowData[i][3]+"'");
			rs.next();
			rowData[i][3] = 48-rs.getInt(1)+"석 남음";
		}
		
		JPanel p2 = new JPanel(new GridLayout(length, 6));
		for(int i=0; i<length; i++) {
			JLabel[] l = new JLabel[5];
			for(int j=0; i<5; i++) {
				l[j] = new JLabel(rowData[i][j], JLabel.CENTER);
				l[j].setPreferredSize(new Dimension(70, 60));
				p2.add(l[j]);
			}
			JPanel bp = new JPanel(new BorderLayout());
			bp.setBorder(new EmptyBorder(15, 0, 15, 0));
			JButton btn = new JButton("예매하기");
			bp.add(btn);
			p2.add(bp);
			
			int r=i;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
					new SeatSelect(rowData[r][6], 1);
				}
			});
		}
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
