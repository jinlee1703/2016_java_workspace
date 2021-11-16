package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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

import org.omg.PortableServer.AdapterActivator;

import Project_DBInterface.DBInterface;

public class SeatSelect extends JFrame implements MouseListener {
	String[] sn = {"1ȣ��","2ȣ��","3ȣ��","4ȣ��","5ȣ��","6ȣ��","7ȣ��","8ȣ��"};
	SpinnerListModel sm = new SpinnerListModel(sn);
	JSpinner spin = new JSpinner();
	JLabel[] lb = new JLabel[6];
	JLabel seat;
	
	public SeatSelect(String tid) throws Exception {
		setTitle("�¼�����");
		setSize(250, 500);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(20, 10, 10, 10));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		spin.setModel(sm);
		spin.setFont(new Font("���� ���", Font.PLAIN, 12));
		spin.setPreferredSize(new Dimension(80, 30));
		p1.add(spin);
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 20, 20));
		p2.setBorder(new EmptyBorder(50, 0, 20, 0));
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from seat where Stateroom_num='"+spin.getValue().toString().substring(0, 1)+"'");
		for(int i=0; i<6; i++) {
			rs.next();
			lb[i] = new JLabel(rs.getString(3));
			lb[i].setBackground(Color.white);
			lb[i].setOpaque(false);
			lb[i].setHorizontalAlignment(lb[i].CENTER);
			lb[i].setBorder(new LineBorder(Color.black));
			lb[i].addMouseListener(this);
			p2.add(lb[i]);
		}
		
		for(int i=0; i<6; i++) {
			lb[i].setBackground(Color.white);
			rs = DBInterface.Stmt.executeQuery("select * from reservation where Train_service_num='"+tid+"'");
			while(rs.next()) {
				if(rs.getInt(4)==(i+1)*Integer.parseInt(spin.getValue().toString().substring(0, 1))) {
					lb[i].setBackground(Color.decode("0x9FFFFF"));
					lb[i].setOpaque(true);
				}
			}
		}
		
		System.out.println(tid);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JButton btn = new JButton("���ÿϷ�");
		p3.add(btn);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
				seat=null;
				
				try {
					for(int i=0; i<6; i++) {
						lb[i].setBackground(Color.white);
						lb[i].setOpaque(false);
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from reservation where Train_service_num='"+tid+"'");
						while(rs.next()) {
							if(rs.getInt(4)==(i+1)*Integer.parseInt(spin.getValue().toString().substring(0, 1))) {
								lb[i].setBackground(Color.decode("0x9FFFFF"));
								lb[i].setOpaque(true);
							}
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(seat==null) {
					JOptionPane.showMessageDialog(null, "�¼��� �������ּ���.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("SELECT train.name,date_format(Departure_time, '%x-%m-%d %h:%i'),format(schedule.Price,'#,##0') FROM train_service join schedule join train on Schedule_num=schedule.id and Train_num=train.id and train_service.id='"+tid+"';");
						rs.next();
						
						int res = JOptionPane.showConfirmDialog(null, "<html>"+TicketReservation.start.getText()+"��"+TicketReservation.end.getText()+"<br>���� : "+rs.getString(1)+"<br>��߽ð� : "+rs.getString(2)+"<br>�¼� : "+seat.getText()+"<br>���� : "+rs.getString(3)+"<br>�����Ͻðڽ��ϱ�?", "", JOptionPane.OK_OPTION);
						
						if(res==JOptionPane.OK_OPTION) {
							rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+Login.id.getText()+"'");
							rs.next(); String i = rs.getString(1);
							
							rs = DBInterface.Stmt.executeQuery("select count(*) from reservation");
							rs.next(); int n = rs.getInt(1)+1;
							
							Date d = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							
							DBInterface.Stmt.execute("INSERT INTO `traindb`.`reservation` (`id`, `User_num`, `Train_service_num`, `Seat_num`, `Reservation_date`) "
									+ "VALUES ('"+n+"', '"+i+"', '"+tid+"', '"+Integer.parseInt(seat.getText().substring(0, 1))*Integer.parseInt(spin.getValue().toString().substring(0, 1)) +"', '"+sdf.format(d)+"');");
							
							JOptionPane.showMessageDialog(null, "���� �Ϸ�Ǿ����ϴ�.");
							
							dispose();
						}
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel label = (JLabel) e.getSource();
		if(label.getBackground()!=Color.white && seat!=label) {
			JOptionPane.showMessageDialog(null, "�̹� ����� �¼��Դϴ�.");
			return;
		}
		
		seat = (JLabel) e.getSource();
		seat.setBackground(Color.pink);
		seat.setOpaque(true);
		
		for(int i=0; i<6; i++) {
			if(! lb[i].getBackground().equals(Color.decode("0x9FFFFF")) && lb[i]!=seat) {
				lb[i].setBackground(Color.white);
				lb[i].setOpaque(false);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
