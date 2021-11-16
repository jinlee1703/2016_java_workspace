package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

public class Main extends JFrame implements ActionListener, Runnable {
	String[] cn = {"서울역","대전역","대구역","부산역","광주역"};
	JComboBox combo = new JComboBox(cn);
	JLabel tl = new JLabel();
	String[] header = {"열차","출발시간","도착역","도착시간"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn1 = new JButton("승차권예매");
	JButton btn2 = new JButton("승차권예매");
	JButton btn3 = new JButton("승차권예매");
	int cnt=0;
	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
	Date nowDate, rowDate;
	
	public Main() {
		setTitle("메인");
		setSize(500, 450);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel cp = new JPanel(new BorderLayout());
		cp.setBorder(new EmptyBorder(0, 0, 0, -19));
		cp.add(combo);
		p1.add(cp, BorderLayout.WEST);
		p1.add(tl, BorderLayout.EAST);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(scroll);
		
		JPanel p3 = new JPanel(new GridLayout(1, 3, 10, 10));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p3.add(btn1); p3.add(btn2); p3.add(btn3);
		
		Thread th = new Thread(this);
		th.start();
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				final Component c =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(this.CENTER);
				
				try {
					rowDate = sdf.parse(table.getValueAt(row, 1).toString());
					long dd = rowDate.getTime() - nowDate.getTime();
					if(cnt%2==0 && dd <= 1800000) {
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
				ComboChange();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			tl.setText("현재시각 : "+sdf.format(new Date()));
			
			try {
				nowDate = sdf.parse(sdf.format(new Date()));
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
			cnt++;
			table.repaint();
		}
	}
	
	public void  ComboChange() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("SELECT train.name,date_format(train_service.Departure_time, '%H:%i'),station.name,date_format(addtime(train_service.Departure_time,Lead_time), '%H:%i') FROM train_service join schedule join station join train where Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id and Departure_time>=current_time() and date(Departure_time)=current_date() and Departure_station='"+combo.getSelectedIndex()+1+"' order by Departure_time;");

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
