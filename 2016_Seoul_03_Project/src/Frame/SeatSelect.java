package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.events.MouseEvent;

import Project_DBInterface.DBInterface;

public class SeatSelect extends JFrame implements MouseListener {
	String[] sn = {"1호차","2호차","호차","4호차","5호차","6호차","7호차","8호차"};
	JSpinner spin = new JSpinner();
	JLabel[] l = new JLabel[6];
	JButton btn = new JButton("선택완료");
	JLabel seat=null;
	String tid;
	String id;
	
	public SeatSelect(String tid, int type) {
		setTitle("좌석선택");
		setSize(250, 500);
		setLocationRelativeTo(null);
		this.tid=tid;
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(20, 10, 10, 10));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		spin.setPreferredSize(new Dimension(80, 30));
		spin.setModel(new SpinnerListModel(sn));
		spin.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		p1.add(spin);
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 20, 20));
		p2.setBorder(new EmptyBorder(50, 0, 20, 0));
		for(int i=0; i<6; i++) {
			l[i] = new JLabel("",JLabel.CENTER);
			l[i].setBorder(new LineBorder(Color.black));
			l[i].addMouseListener(this);
			p2.add(l[i]);
		}
		
		JPanel p3 = new JPanel();
		p3.add(btn);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		spinSetting();
		
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				spinSetting();
			}
		});
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(seat==null) {
					JOptionPane.showMessageDialog(null, "좌석을 선택해주세요.");
					return;
				} else {
					if(type==1) {
						try {
							ResultSet rs = DBInterface.Stmt.executeQuery("SELECT distinct train.name,date(Departure_time),date_format(Departure_time, '%H:%i'),date(addtime(Departure_time,Lead_time)),date_format(addtime(Departure_time,Lead_time),'%H:%i'),format(Price,'#,##0'),train_service.id FROM train_service join train join schedule join station on Schedule_num=schedule.id and Train_num=train.id where train_service.id='"+tid+"' order by Departure_time;");
							rs.next();
							
							String st1 = "<html>"+MapSelect.ss.getText()+"→"+MapSelect.es.getText();
							String st2 = "<br>열차 : "+rs.getString(1);
							String st3 = "<br>출발시간 : "+rs.getString(2)+" "+rs.getString(3);
							String st4 = "<br>좌석 : "+seat.getText();
							String st5 = "<br>가격 : "+rs.getString(6);
							String st6 = "<br>예매하시겠습니까?</html>";
							
							int res = JOptionPane.showConfirmDialog(null, st1+st2+st3+st4+st5+st6, "", JOptionPane.OK_CANCEL_OPTION);
							
							if(res==JOptionPane.OK_OPTION) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								DBInterface.Stmt.execute("INSERT INTO `traindb`.`reservation` (`User_num`, `Train_service_num`, `Seat_num`, `Reservation_date`) "
										+ "VALUES ('"+id+"', '"+tid+"', '"+spin.getValue().toString().substring(0, 1)+"', '"+sdf.format(new Date())+"');");
								JOptionPane.showMessageDialog(null, "예매 완료되었습니다.");
								dispose();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						try {
							ResultSet rs = DBInterface.Stmt.executeQuery("SELECT distinct train.name,date(Departure_time),date_format(Departure_time, '%H:%i'),date(addtime(Departure_time,Lead_time)),date_format(addtime(Departure_time,Lead_time),'%H:%i'),format(Price,'#,##0'),train_service.id FROM train_service join train join schedule join station on Schedule_num=schedule.id and Train_num=train.id where train_service.id='"+tid+"' order by Departure_time;");
							rs.next();
							
							String st1 = "<html>"+MapSelect.ss.getText()+"→"+MapSelect.es.getText();
							String st2 = "<br>열차 : "+rs.getString(1);
							String st3 = "<br>출발시간 : "+rs.getString(2)+" "+rs.getString(3);
							String st4 = "<br>좌석 : "+seat.getText();
							String st5 = "<br>가격 : "+rs.getString(6);
							String st6 = "<br>예매변경하시겠습니까?</html>";
							
							int res = JOptionPane.showConfirmDialog(null, st1+st2+st3+st4+st5+st6, "", JOptionPane.OK_CANCEL_OPTION);
							
							if(res==JOptionPane.OK_OPTION) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								DBInterface.Stmt.execute("UPDATE `traindb`.`reservation` SET `Train_service_num`='"+tid+"', `Seat_num`='"+spin.getValue().toString().substring(0, 1)+"', `Reservation_date`='"+sdf.format(new Date())+"' WHERE `id`='"+id+"';");
								JOptionPane.showMessageDialog(null, "예매 완료되었습니다.");
								dispose();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel label = (JLabel) e.getSource();
		
		if(label.getBackground().equals(Color.decode("0x9FFFFF"))) {
			JOptionPane.showMessageDialog(null, "이미 예약된 좌석입니다.");
		} else {
			seat = label;
			seat.setBackground(Color.pink);
			seat.setOpaque(true);
			for(int i=0; i<6; i++) {
				if(! l[i].getBackground().equals(Color.decode("0x9FFFFF")) && l[i]!=seat) {
					l[i].setBackground(Color.white);
					l[i].setOpaque(false);
				}
			}
		}
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void spinSetting() {
		try {
			seat=null;
			
			for(int i=0; i<6; i++) {
				l[i].setOpaque(false);
				l[i].setBackground(Color.white);
			}
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from seat where Stateroom_num='"+spin.getValue().toString().substring(0, 1)+"'");
			
			while(rs.next()) {
				l[rs.getRow()-1].setText(rs.getString(3));
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+Login.id.getText()+"'");
			rs.next();
			id=rs.getString(1);
			
			rs = DBInterface.Stmt.executeQuery("select * from reservation where User_num='"+id+"' and Train_service_num='"+tid+"'");
			
			while(rs.next()) {
				for(int i=0; i<6; i++) {
					if(rs.getInt(4)==(i+1)*Integer.parseInt(spin.getValue().toString().substring(0, 1))) {
						l[i].setBackground(Color.decode("0x9FFFFF"));
						l[i].setOpaque(true);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
