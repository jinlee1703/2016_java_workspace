package Project_Frame;

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
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Project_DBInterface.DBInterface;

public class SeatSelect extends JFrame {
	String[] sn = {"1호차","2호차","3호차","4호차","5호차","6호차","7호차","8호차"};
	SpinnerListModel model = new SpinnerListModel(sn);
	JSpinner spin = new JSpinner(model);
	JLabel[] s = new JLabel[6];
	JButton btn = new JButton("선택완료");
	String tid;
	JLabel SelectSeat=null;
	
	public SeatSelect(String tid, int type) {
		this.tid = tid;
		setTitle("좌석선택");
		setSize(300, 600);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		spin.setPreferredSize(new Dimension(70, 30));
		p1.add(spin);
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 20, 20));
		for(int i=0; i<6; i++) {
			s[i] = new JLabel("",JLabel.CENTER);
			s[i].setBorder(new LineBorder(Color.black));
			p2.add(s[i]);
		}
		
		JPanel p3 = new JPanel();
		p3.add(btn);
		
		spinChange();
		
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				spinChange();
			}
		});
		
		for(int i=0; i<6; i++) {
			s[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					JLabel label = (JLabel) e.getSource();
					if(label.getBackground().equals(Color.decode("0x9FFFFF"))) {
						JOptionPane.showMessageDialog(null, "이미 예약된 좌석입니다.");
					} else {
						SelectSeat = label;
						SelectSeat.setBackground(Color.pink);
						for(int i=0; i<6; i++) {
							if(! s[i].getBackground().equals(Color.decode("0x9FFFFF")) || s[i]!=SelectSeat) {
								s[i].setBackground(Color.white);
								s[i].setOpaque(false);
							}
						}
					}
				}
			});
		}
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(SelectSeat==null) {
					JOptionPane.showMessageDialog(null, "좌석을 선택해주세요.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("SELECT train.name,date(train_service.Departure_time),date_format(train_service.Departure_time, '%H:%i'),date(addtime(train_service.Departure_time,Lead_time)),date_format(addtime(train_service.Departure_time,Lead_time), '%H:%i'),format(price,'#,##0'),train_service.id FROM train_service join schedule join station join train where Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id and Departure_time>=current_time() and train_service.id='"+tid+"' order by Departure_time;");
						
						String st1 = "<html>"+Map.ss.getText()+"→"+Map.es.getText();
						String st2 = "<br>열차 : "+rs.getString(1);
						String st3 = "<br>출발시간 : "+rs.getString(2)+" "+rs.getString(3);
						String st4 = "<br>좌석"+SelectSeat.getText();
						String st5 = "<br>가격"+rs.getString(6);
						String st6 = "<br>예매하시겠습니까?</html>";
						
						int res = JOptionPane.showConfirmDialog(null, st1+st2+st3+st4+st5+st6, "", JOptionPane.OK_CANCEL_OPTION);
						
						if(res==JOptionPane.OK_OPTION) {
							rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+Login.id.getText()+"'");
							rs.next();
							DBInterface.Stmt.execute("INSERT INTO `traindb`.`reservation` (`User_num`, `Train_service_num`, `Seat_num`, `Reservation_date`) "
									+ "VALUES ('"+rs.getString(1)+"', '"+tid+"', '"+SelectSeat.getText()+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"');");
						}
						
						JOptionPane.showMessageDialog(null, "예매 완료되었습니다.");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void spinChange() {
		try {
			for(int i=0; i<6; i++) {
				s[i].setText("");
				s[i].setBackground(Color.white);
				s[i].setOpaque(false);
			}
			
			SelectSeat=null;
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from seat where Stateroom_num='"+spin.getValue().toString().substring(0, 1)+"'");
			
			while(rs.next()) {
				s[rs.getRow()-1].setText(rs.getString(3));
				s[rs.getRow()-1].setBackground(Color.white);
				s[rs.getRow()-1].setOpaque(false);
			}
			
			for(int i=0; i<6; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from reservation where Train_service_num='"+tid+"' and Seat_num='"+(i+1)*Integer.parseInt(spin.getValue().toString().substring(0, 1))+"'");
				if(rs.next()) {
					s[i].setBackground(Color.decode("0x9FFFFF"));
					s[i].setOpaque(true);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
