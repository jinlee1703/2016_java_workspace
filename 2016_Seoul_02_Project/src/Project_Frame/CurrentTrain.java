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
import javax.swing.border.LineBorder;

import Project_DBInterface.DBInterface;

public class CurrentTrain extends JFrame {
	public CurrentTrain() throws Exception {
		setTitle("조회결과");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(1, 6));
		JLabel[] tl = new JLabel[6];
		String[] tln = {"열차","출발역","도착역","좌석현황","가격",""};
		for(int i=0; i<5; i++) {
			tl[i] = new JLabel(tln[i]);
			tl[i].setOpaque(true);
			tl[i].setPreferredSize(new Dimension(70, 30));
			tl[i].setHorizontalAlignment(tl[i].CENTER);
			tl[i].setForeground(Color.white);
			tl[i].setBackground(Color.darkGray);
			tl[i].setBorder(new LineBorder(Color.white));
			
			p1.add(tl[i]);
		}
		tl[5] = new JLabel(tln[5]);
		p1.add(tl[5]);
		p1.setBorder(new EmptyBorder(0, 0, 0, 15));
		
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from station where name='"+TicketReservation.start.getText()+"'");
		rs.next();
		String a = rs.getString(1);
		
		rs = DBInterface.Stmt.executeQuery("select * from station where name='"+TicketReservation.end.getText()+"'");
		rs.next();
		String b = rs.getString(1);
		
		String c="";
		if(! TicketReservation.combo.getSelectedItem().toString().equals("전체")) {
			c = " and train.name='"+TicketReservation.combo.getSelectedItem().toString()+"'";
		}
		
		int length = 0;
		rs = DBInterface.Stmt.executeQuery("SELECT distinct train.name,date(Departure_time),date_format(time(Departure_time),'%H:%i'),Date(addtime(Departure_time,Lead_time)),date_format(time(addtime(Departure_time,Lead_time)),'%H:%i'),format(schedule.Price,'#,##0'),train_service.id FROM train_service join train join station join schedule on Schedule_num=schedule.id and schedule.Train_num=train.id and schedule.Departure_station='"+a+"' and schedule.Arrival_station='"+b+"' and train_service.Departure_time>=curtime() "+c+" order by train_service.Departure_time;");
		if(rs.last())
			length=rs.getRow();
		
		rs.beforeFirst();
		
		String[][] rowData = new String[length][6];
		int idx=0;
		while(rs.next()) {
			rowData[idx][0] = rs.getString(1);
			rowData[idx][1] = "<html>"+TicketReservation.start.getText()+"<br>"+rs.getString(2)+"<br>"+rs.getString(3)+"</html>";
			rowData[idx][2] = "<html>"+TicketReservation.end.getText()+"<br>"+rs.getString(4)+"<br>"+rs.getString(5)+"</html>";
			rowData[idx][3] = rs.getString(7); 
			rowData[idx][4] = rs.getString(6);
			rowData[idx][5] = rs.getString(7);
			idx++;
		}
		
		for(int i=0; i<rowData.length; i++) {
			rs = DBInterface.Stmt.executeQuery("select count(*) from reservation where Train_service_num='"+rowData[i][3]+"'");
			rs.next();
			rowData[i][3] = String.valueOf(48 - rs.getInt(1))+"석 남음";
		}
		
		JPanel p2 = new JPanel(new GridLayout(rs.getRow()-1, 6));
		for(int j=0; j<rowData.length; j++) {
			JLabel[] laa = new JLabel[5];
			for(int i=0; i<5; i++) {
				laa[i] = new JLabel(rowData[j][i]);
				laa[i].setHorizontalAlignment(laa[i].CENTER);
				laa[i].setPreferredSize(new Dimension(70, 60));
				p2.add(laa[i]);
			}
			JPanel pp = new JPanel(new BorderLayout());
			pp.setBorder(new EmptyBorder(20, 0, 20, 0));
			JButton bt = new JButton("예매하기");
			
			int rr=j;
			bt.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						dispose();
						new SeatSelect(rowData[rr][5]);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			bt.setPreferredSize(new Dimension(70, 30));
			pp.add(bt);
			p2.add(pp);
		}
		
		JScrollPane scroll2 = new JScrollPane(p2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(scroll2, BorderLayout.CENTER);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
