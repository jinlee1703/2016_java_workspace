package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class ClientSelect extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("고객명");
	JLabel label2 = new JLabel("고객목록");
	JTextField text = new JTextField(10);
	JButton[] btn = new JButton[4];
	String[] bn = {"검색","고객헤어정보","주문","나가기"};
	static String[] header = {"고객번호","고객명","생년월일","핸드폰번호","주소","메일","이달생일"};
	static DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	static JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public ClientSelect() {
		setTitle("고객검색");
		setSize(600, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(label1); p1.add(text); p1.add(btn[0]);
		
		JPanel p2 = new JPanel(new BorderLayout());
		JPanel p21 = new JPanel(new FlowLayout(FlowLayout.LEFT)); p21.add(label2);
		JPanel p22 = new JPanel(new BorderLayout()); p22.add(scroll, BorderLayout.CENTER);
		p2.add(p21, BorderLayout.NORTH); p2.add(p22, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3.add(btn[1]); p3.add(btn[2]); p3.add(btn[3]);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(p2, BorderLayout.CENTER);
		basePanel.add(p3, BorderLayout.SOUTH);
		
		add(basePanel);
		
		Init();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			TableSearch();
		} else if(bt==btn[1]) {
			
		} else if(bt==btn[2]) {
			new SalesSelect();
		} else if(bt==btn[3]) {
			this.dispose();
		}
		
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from client");
			
			String[] newRow = new String[7];
			Date d = new Date();
			SimpleDateFormat s = new SimpleDateFormat("MM");
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				newRow[4] = rs.getString(5);
				newRow[5] = rs.getString(6);
				if(rs.getString(3).substring(5, 7).equals(s.format(d))) {
					newRow[6] = "O";
				} else {
					newRow[6] = "";
				}
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void TableSearch() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where name like '%"+text.getText()+"%'");
			
			String[] newRow = new String[7];
			Date d = new Date();
			SimpleDateFormat s = new SimpleDateFormat("MM");
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				newRow[4] = rs.getString(5);
				newRow[5] = rs.getString(6);
				if(rs.getString(3).substring(5, 7).equals(s.format(d))) {
					newRow[6] = "O";
				} else {
					newRow[6] = "";
				}
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
