package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import Project_DBInterface.DBInterface;

public class Seat extends JFrame {
	String[] sn = {"1호차","2호차","3호차","4호차","5호차","6호차","7호차","8"};
	SpinnerListModel model = new SpinnerListModel(sn);
	JSpinner spin = new JSpinner();
	JLabel[] l = new JLabel[6];
	JButton btn = new JButton("선택완료");
	String tid;
	JLabel ss=null;
	
	public Seat(String tid, int type) {
		this.tid=tid;
		setTitle("좌석선택");
		setSize(250, 550);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 5, 10, 5));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p1.setBorder(new EmptyBorder(0, 0, 0, 30));
		spin.setModel(model);
		spin.setPreferredSize(new Dimension(80, 30));
		p1.add(spin);
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 10, 10));
		for(int i=0; i<6; i++) {
			l[i] = new JLabel("",JLabel.CENTER);
			l[i].setBorder(new LineBorder(Color.black));
			l[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					JLabel s = (JLabel) e.getSource();
					if(s.getBackground().equals(Color.decode("0x9FFFFF"))) {
						JOptionPane.showMessageDialog(null, "이미 예약된 좌석입니다.");
					} else {
						ss=s;
						ss.setBackground(Color.pink);
						ss.setOpaque(true);
						for(int i=0; i<6; i++) {
							if(! l[i].equals(ss) && ! l[i].getBackground().equals(Color.decode("0x9FFFFF"))) {
								l[i].setOpaque(false);
								l[i].setBackground(Color.white);
							}
						}
					}
				}
			});
			p2.add(l[i]);
		}
		
		JPanel p3 = new JPanel();
		p3.add(btn);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		spin.addChangeListener(new  ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				spinChange();
			}
		});
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(ss==null) {
					JOptionPane.showMessageDialog(null, "좌석을 선택해주세요.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select train.name,date(train_service.Departure_time),date_format(train_service.Departure_time, '%H:%i'),date(addtime(train_service.Departure_time,schedule.Lead_time)),date_format(addtime(train_service.Departure_time,schedule.Lead_time), '%H:%i'),format(price,'#,##0'),train_service.id from train_service join schedule join station join train on Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id where Departure_time>current_time() and train_service.id='"+tid+"'");
						rs.next();
						String st1 = Map.startStation.getText()+"→"+Map.endStation.getText();
						String st2 = "열차 : "+rs.getString(1);
						String st3 = "출발시간 : "+rs.getString(2)+" "+rs.getString(3);
						String st4 = "좌석 : "+ss.getText();
						String st5 = "가격 : "+rs.getShort(6);
						
						if(type==1) {
							int res = JOptionPane.showConfirmDialog(null, st1+"\n"+st2+"\n"+st3+"\n"+st4+"\n"+st5+"\n"+"예매하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
							
							if(res==JOptionPane.YES_OPTION) {
								rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+Login.id.getText()+"'"); rs.next(); String a=rs.getString(1);
								String b=tid;
								int c = 0;
								for(int i=0; i<6; i++) {
									if(ss.equals(l[i])) {
										c=Integer.parseInt(spin.getValue().toString().substring(0, 1))*(i+1);
									}
								}
								DBInterface.Stmt.execute("INSERT INTO `traindb`.`reservation` (`User_num`, `Train_service_num`, `Seat_num`, `Reservation_date`) "
										+ "VALUES ('"+a+"', '"+b+"', '"+c+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"');");
								JOptionPane.showMessageDialog(null, "예매 완료되었습니다.");
								
								dispose();
							}
						} else {
							int res = JOptionPane.showConfirmDialog(null, st1+"\n"+st2+"\n"+st3+"\n"+st4+"\n"+st5+"\n"+"예매변경겠습니까?", "", JOptionPane.YES_NO_OPTION);
							
							if(res==JOptionPane.YES_OPTION) {
								rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+Login.id.getText()+"'"); rs.next(); String a=rs.getString(1);
								String b=tid;
								int c = 0;
								for(int i=0; i<6; i++) {
									if(ss.equals(l[i])) {
										c=Integer.parseInt(spin.getValue().toString().substring(0, 1))*(i+1);
									}
								}
								DBInterface.Stmt.execute("UPDATE `traindb`.`reservation` SET `Train_service_num`='"+tid+"', `Seat_num`='"+c+"', `Reservation_date`='2016-03-051' WHERE `id`='"+TicketSelect.st+"';");
								JOptionPane.showMessageDialog(null, "예매 완료되었습니다.");
								
								dispose();
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		spinChange();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void spinChange() {
		for(int i=0; i<6; i++) {
			l[i].setText("");
			l[i].setOpaque(false);
			l[i].setBackground(Color.white);
		}
		ss=null;
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from seat where Stateroom_num='"+spin.getValue().toString().substring(0, 1)+"'");
			for(int i=0; i<6; i++) {
				rs.next();
				l[i].setText(rs.getString(3));
			}
			
			for(int i=0; i<6; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from reservation where Train_service_num='"+tid+"' and Seat_num='"+(i+1)*Integer.parseInt(spin.getValue().toString().substring(0, 1))+"'");
				if(rs.next()) {
					l[i].setOpaque(true);
					l[i].setBackground(Color.decode("0x9FFFFF"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

