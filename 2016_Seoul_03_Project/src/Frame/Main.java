package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class Main extends JFrame implements ActionListener,Runnable {
	String[] cn = {"서울역","대전역","대구역","부산역","광주역"};
	JComboBox combo = new JComboBox(cn);
	JLabel tl = new JLabel();
	String[] header = {"열차","출발시간","도착역","도착시간"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn1 = new JButton("승차권예매");
	JButton btn2 = new JButton("승차권확인");
	JButton btn3 = new JButton("로그아웃");
	Date d = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	Date rd, nd;
	int t=0;
	
	public Main() {
		setTitle("메인");
		setSize(500, 500);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 10, 50, 10));
		
		JPanel p1 = new JPanel(new BorderLayout());
		
		JPanel p11 = new JPanel(new BorderLayout());
		p11.setPreferredSize(new Dimension(100, 30));
		p11.setBorder(new EmptyBorder(0, 0, 0, -19));
		p11.add(combo);
		p1.add(p11, BorderLayout.WEST); p1.add(tl, BorderLayout.EAST);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 0, 80, 0));
		p2.add(scroll);
		
		JPanel p3 = new JPanel(new GridLayout(1, 3, 10, 10));
		p3.setPreferredSize(new Dimension(480, 40));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p3.add(btn1); p3.add(btn2); p3.add(btn3);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		TableInsert();
		
		Thread th = new Thread(this);
		th.start();
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(this.CENTER);
				
				try {
					rd = sdf.parse(table.getValueAt(row, 1).toString());
					long dd = rd.getTime()-nd.getTime();
					if(t%2==0 && dd<= 1800000) {
						c.setBackground(Color.pink);
					} else {
						c.setBackground(Color.white);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return c;
			}
		});
		
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TableInsert();
			}
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			new MapSelect();
		} else if(bt==btn2) {
			new TicketSelect();
		} else if(bt==btn3) {
			this.dispose();
			new Main();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			tl.setText("현재시각 : "+sdf.format(d));
			try {
				nd=sdf.parse(sdf.format(d));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t++;
			table.repaint();
		}
	}
	
	public void TableInsert() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from station where name='"+combo.getSelectedItem().toString()+"'"); rs.next();
			String tn = rs.getString(1);
			
			rs = DBInterface.Stmt.executeQuery("SELECT train.name,date_format(Departure_time, '%H:%i'),station.name,date_format(addtime(Departure_time,Lead_time),'%H:%i') FROM train_service join train join schedule join station on Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id and Departure_time>=current_time() and date(Departure_time)=curdate() and Departure_station='"+tn+"' order by Departure_time;");
			
			String[] newRow = new String[4];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
