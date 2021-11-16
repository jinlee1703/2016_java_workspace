package Frame;

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

public class List extends JFrame {
	String[] ln = {"열차","출발역","도착역","좌석현황","가격",""};
	JLabel[] l = new JLabel[6];
	
	public List() throws Exception {
		setTitle("조회결과");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 5, 5, 0));
		
		JPanel p1 = new JPanel(new GridLayout(1, 6));
		p1.setBorder(new EmptyBorder(0, 0, 0, 15));
		for(int i=0; i<ln.length; i++) {
			l[i] = new JLabel(ln[i], JLabel.CENTER);
			if(i!=5) {
				l[i].setOpaque(true);
				l[i].setPreferredSize(new Dimension(70, 30));
				l[i].setForeground(Color.white);
				l[i].setBackground(Color.darkGray);
				l[i].setBorder(new LineBorder(Color.white));
			}
			p1.add(l[i]);
		}
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from station where name='"+MapSelect.ss.getText()+"'");
		rs.next();
		String sn=rs.getString(1);
		
		rs = DBInterface.Stmt.executeQuery("select * from station where name='"+MapSelect.es.getText()+"'");
		rs.next();
		String en=rs.getString(1);
		
		String tn;
		if(MapSelect.combo.getSelectedItem().toString().equals("전체")) {
			tn="%";
		} else {
			tn=MapSelect.combo.getSelectedItem().toString();
		}
		
		rs = DBInterface.Stmt.executeQuery("SELECT distinct train.name,date(Departure_time),date_format(Departure_time, '%H:%i'),date(addtime(Departure_time,Lead_time)),date_format(addtime(Departure_time,Lead_time),'%H:%i'),format(Price,'#,##0'),train_service.id FROM train_service join train join schedule join station on Schedule_num=schedule.id and Train_num=train.id and Departure_time>=current_time() and Departure_station='"+sn+"' and Arrival_station='"+en+"' and train.name like '"+tn+"' order by Departure_time;");
		rs.last();
		int length = rs.getRow();
		
		rs.beforeFirst();
		
		String[][] rowData = new String[length][6];
		while(rs.next()) {
			rowData[rs.getRow()-1][0] = rs.getString(1);
			rowData[rs.getRow()-1][1] = "<html>"+MapSelect.ss.getText()+"<br>"+rs.getString(2)+"<br>"+rs.getString(3)+"</html>";
			rowData[rs.getRow()-1][2] = "<html>"+MapSelect.es.getText()+"<br>"+rs.getString(4)+"<br>"+rs.getString(5)+"</html>";
			rowData[rs.getRow()-1][3] = rs.getString(7);
			rowData[rs.getRow()-1][4] = rs.getString(6);
			rowData[rs.getRow()-1][5] = rs.getString(7);
		}
		
		for(int i=0; i<rowData.length; i++) {
			rs = DBInterface.Stmt.executeQuery("select count(*) from reservation where Train_service_num='"+rowData[i][3]+"'");
			rs.next();
			rowData[i][3] = 48-rs.getInt(1)+"석 남음";
		}
		
		JPanel p2 = new JPanel(new GridLayout(rowData.length, 6));
		for(int i=0; i<rowData.length; i++) {
			JLabel[] ll = new JLabel[5];
			for(int j=0; j<5; j++) {
				ll[j] = new JLabel(rowData[i][j], JLabel.CENTER);
				ll[j].setPreferredSize(new Dimension(70, 60));
				p2.add(ll[j]);
			}
			
			JPanel bp = new JPanel(new BorderLayout());
			bp.setBorder(new EmptyBorder(20, 0, 20, 0));
			JButton btn = new JButton("예매하기");
			bp.add(btn);
			
			int r=i;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
					new SeatSelect(rowData[r][5], 1);
				}
			});
			p2.add(bp);
		}
		
		JScrollPane scroll = new JScrollPane(p2);
	
		p.add(p1, BorderLayout.NORTH);
		p.add(scroll, BorderLayout.CENTER);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
