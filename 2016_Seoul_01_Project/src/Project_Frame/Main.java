package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class Main extends JFrame implements ActionListener {
	JComboBox combo = new JComboBox();
	JLabel tl = new JLabel();
	String[] header = {"열차","출발시간","도착역","도착시간"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton[] btn = new JButton[3];
	String[] bn = {"승차권예매","승차권확인","로그아웃"};
	
	boolean isSelected;
	boolean hasFocus;
	int row;
	int column;
	
	public Main() {
		setTitle("메인");
		setSize(500, 450);
		setLocationRelativeTo(null);
		
		DefaultTableCellRenderer pinkrenderer = new DefaultTableCellRenderer();
		pinkrenderer.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
		pinkrenderer.setHorizontalAlignment(pinkrenderer.CENTER);
		Component cellComponent = pinkrenderer.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
		
		try {
			table.setDefaultRenderer(Class.forName("java.lang.Object"), pinkrenderer);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		p.setBorder(new EmptyBorder(5, 0, 0, 0));
		
		JPanel p1 = new JPanel(new GridLayout(1, 2, 250, 0));
		p1.setPreferredSize(new Dimension(470, 28));
		JPanel p11 = new JPanel(new BorderLayout());
		p11.setPreferredSize(new Dimension(100, 30));
		p11.setBorder(new EmptyBorder(0, 0, 0, -19));
		p11.add(combo);
		
		p1.add(p11); p1.add(tl);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setPreferredSize(new Dimension(470, 220));
		p2.add(scroll);
		
		JPanel p3 = new JPanel(new GridLayout(1, 3, 10, 5));
		p3.setBorder(new EmptyBorder(60, 0, 0, 0));
		p3.setPreferredSize(new Dimension(470, 100));
		for(int i=0; i<3; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			p3.add(btn[i]);
		}
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		add(p);
		
		ComboInit();
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ComboChange();
			}
		});
		
		Thread th = new Thread(new Runnable() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int n=0;
				
				while(true) {
					Date d = new Date();
					SimpleDateFormat f = new SimpleDateFormat("HH:mm");
					tl.setText("현재시각 : "+f.format(d));
					
					if(n==0) {
						table.repaint();
						cellComponent.setBackground(Color.pink);
						n=1;
					} else if(n==1) {
						table.repaint();
						cellComponent.setBackground(Color.white);
						n=0;
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		th.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new ReservationMap();
		}
	}
	
	public void ComboInit() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from station");
			
			while(rs.next()) {
				combo.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ComboChange();
	}
	
	public void ComboChange() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from station where name='"+combo.getSelectedItem().toString()+"'");
			rs.next(); String n = rs.getString(1);
			
			rs = DBInterface.Stmt.executeQuery("SELECT train.name,date_format(Departure_time,'%H:%i:%s'),station.name,date_format(addtime(Departure_time,Lead_time),'%H:%i:%s') FROM train join schedule join train_service join station on train_service.Schedule_num=Schedule.id and train.id=train_num and schedule.Arrival_station=station.id where Departure_station='"+n+"' and DATE(train_service.Departure_time)=CURDATE() and current_time()<time(train_service.Departure_time) order by date_format(Departure_time,'%H:%i:%s');");
			
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