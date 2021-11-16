package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Project_DBInterface.DBInterface;

public class TicketSelect extends JFrame implements ActionListener {
	String[] header = {"","","예매일","열차","출발역","출발시간","도착역","도착시간","좌석번호","가격"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn1 = new JButton("예매변경");
	JButton btn2 = new JButton("예매취소");
	JButton btn3 = new JButton("닫기");
	static int st=0;
	
	public TicketSelect() {
		setTitle("승차권 확인");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btn1.setPreferredSize(new Dimension(80, 30));
		btn2.setPreferredSize(new Dimension(80, 30));
		btn3.setPreferredSize(new Dimension(80, 30));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p1.add(btn1); p1.add(btn2); p1.add(btn3);
		
		p.add(scroll, BorderLayout.CENTER);
		p.add(p1, BorderLayout.SOUTH);
		
		add(p);
		
		TableSettig();
		
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter ts = new TableRowSorter(model);
		table.setRowSorter(ts);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				st=0;
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(table.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "승차권을 선택해주세요.");
			} else {
				st = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
				new Map();
			}
		} else if(bt==btn2) {
			if(table.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "승차권을 선택해주세요.");
			} else {
				int res = JOptionPane.showConfirmDialog(null, "예매를 취소하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
				if(res==JOptionPane.YES_OPTION) {
					try {
						DBInterface.Stmt.execute("DELETE FROM `traindb`.`reservation` WHERE `id`='"+table.getModel().getValueAt(table.getSelectedRow(), 0)+"';");
						JOptionPane.showMessageDialog(null, "예매가 취소되었습니다.");
						TableSettig();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} else if(bt==btn3) {
			this.dispose();
		}
	}
	
	public void TableSettig() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select reservation.id,train_service.id,Reservation_date,train.name,Departure_station,date(train_service.Departure_time),date_format(train_service.Departure_time, '%H:%i'),Arrival_station,date(addtime(train_service.Departure_time,schedule.Lead_time)),date_format(addtime(train_service.Departure_time,schedule.Lead_time), '%H:%i'),seat.name,format(price,'#,##0') from train_service join schedule join station join train join seat join user join reservation on Schedule_num=schedule.id and Arrival_station=station.id and Train_num=train.id and User_num=user.id and Train_service_num=train_service.id and Seat_num=seat.id where user.uID='"+Login.id.getText()+"' and Departure_time>current_time() order by Departure_time;");
			rs.last();
			String[][] newRow = new String[rs.getRow()][10];
			rs.beforeFirst();
			while(rs.next()) {
				newRow[rs.getRow()-1][0] = rs.getString(1);
				newRow[rs.getRow()-1][1] = rs.getString(2);
				newRow[rs.getRow()-1][2] = rs.getString(3);
				newRow[rs.getRow()-1][3] = rs.getString(4);
				newRow[rs.getRow()-1][4] = rs.getString(5);	//
				newRow[rs.getRow()-1][5] = rs.getString(6)+" "+rs.getString(7);
				newRow[rs.getRow()-1][6] = rs.getString(8);	//
				newRow[rs.getRow()-1][7] = rs.getString(9)+" "+rs.getString(10);
				newRow[rs.getRow()-1][8] = rs.getString(11);
				newRow[rs.getRow()-1][9] = rs.getString(12);
			}
			
			for(int i=0; i<newRow.length; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[i][4]+"'");
				rs.next(); newRow[i][4] = rs.getString(2);
				
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[i][6]+"'");
				rs.next(); newRow[i][6] = rs.getString(2);
				
				model.addRow(newRow[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
