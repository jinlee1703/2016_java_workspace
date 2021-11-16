package Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Project_DBInterface.DBInterface;

public class TicketSelect extends JFrame implements ActionListener {
	static String[] header = {"예매일","열차","출발역","출발시간","도착역","도착시간","좌석번호","가격",""};
	static DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn1 = new JButton("예매변경");
	JButton btn2 = new JButton("예매취소");
	JButton btn3 = new JButton("닫기");
	String rt;
	
	public TicketSelect() {
		setTitle("승차권확인");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(scroll);
		
		JPanel pp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel p2 = new JPanel(new GridLayout(1, 3));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p2.add(btn1);
		p2.add(btn2);
		p2.add(btn3);
		
		pp.add(p2);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(pp, BorderLayout.SOUTH);
		
		add(p);
		
		tableSetting();
		
		table.removeColumn(table.getColumnModel().getColumn(8));
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter ts = new TableRowSorter(table.getModel());
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
			int res = JOptionPane.showConfirmDialog(null, "예매를 변경하시겠습니까?", "", JOptionPane.OK_CANCEL_OPTION);
			
			if(res==JOptionPane.OK_OPTION) {
				rt = (String) table.getModel().getValueAt(table.getSelectedRow(), 8);
				new MapSelect();
			}
		} else if(bt==btn2) {
			int res = JOptionPane.showConfirmDialog(null, "예매를 취소하시겠습니까?", "", JOptionPane.OK_CANCEL_OPTION);
			
			if(res==JOptionPane.OK_OPTION) {
				try {
					DBInterface.Stmt.execute("DELETE FROM `traindb`.`reservation` WHERE `id`='"+table.getModel().getValueAt(table.getSelectedRow(), 8)+"';");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "예매가 취소되었습니다.");
				
				tableSetting();
			}
		} else if(bt==btn3) {
			this.dispose();
		}
	}
	
	public static void tableSetting() {
		try {
			model.setNumRows(0);
			ResultSet rs = DBInterface.Stmt.executeQuery("SELECT distinct Reservation_date,train.name,Departure_station,date(Departure_time),date_format(Departure_time, '%H:%i'),Arrival_station,date(addtime(Departure_time,Lead_time)),date_format(addtime(Departure_time,Lead_time),'%H:%i'),seat.name,format(Price,'#,##0'),train_service.id,reservation.id FROM train_service join train join schedule join station join reservation join user join seat on Schedule_num=schedule.id and Train_num=train.id and User_num=user.id and Train_service_num=train_service.id and Seat_num=seat.seat and user.uID='"+Login.id.getText()+"' and Departure_time>=current_time() order by Departure_time;");
			rs.last();
			
			String[][] newRow = new String[rs.getRow()][9];
			rs.beforeFirst();
			while(rs.next()) {
				newRow[rs.getRow()-1][0] = rs.getString(1);
				newRow[rs.getRow()-1][1] = rs.getString(2);
				newRow[rs.getRow()-1][2] = rs.getString(3);
				newRow[rs.getRow()-1][3] = rs.getString(4)+" "+rs.getString(5);
				newRow[rs.getRow()-1][4] = rs.getString(6);
				newRow[rs.getRow()-1][5] = rs.getString(7)+" "+rs.getString(8);
				newRow[rs.getRow()-1][6] = rs.getString(9);
				newRow[rs.getRow()-1][7] = rs.getString(10);
				newRow[rs.getRow()-1][8] = rs.getString(11);
			}
			
			for(int i=0; i<newRow.length; i++) {
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[3]+"'");
				if(rs.next()) {
					newRow[i][3] = rs.getString(2);
				}
				
				rs = DBInterface.Stmt.executeQuery("select * from station where id='"+newRow[5]+"'");
				if(rs.next()) {
					newRow[i][5] = rs.getString(2);
				}
				
				model.addRow(newRow[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
