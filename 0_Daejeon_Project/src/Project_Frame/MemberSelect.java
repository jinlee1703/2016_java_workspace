package Project_Frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class MemberSelect extends JFrame  {
	JTextField t1 = new JTextField(5);
	JTextField t2 = new JTextField(5);
	JButton btn = new JButton("검색");
	String[] header = {"아이디","비밀번호","이름","연락처","주소","포인트"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public MemberSelect() {
		setTitle("고객조회");
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.add(new JLabel("포인트")); p1.add(t1); p1.add(new JLabel("~")); p1.add(t2); p1.add(btn);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(scroll);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		
		add(p);
		
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member");
			
			String newRow[] = new String[6];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				newRow[4] = rs.getString(5);
				newRow[5] = rs.getString(6);
				
				model.addRow(newRow);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t1.getText().equals("") && t2.getText().equals("")) {
					try {
						model.setNumRows(0);
						
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member");
						
						String newRow[] = new String[6];
						
						while(rs.next()) {
							newRow[0] = rs.getString(1);
							newRow[1] = rs.getString(2);
							newRow[2] = rs.getString(3);
							newRow[3] = rs.getString(4);
							newRow[4] = rs.getString(5);
							newRow[5] = rs.getString(6);
							
							model.addRow(newRow);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(t1.equals("")) {
					try {
						model.setNumRows(0);
						
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where point<='"+t2.getText()+"'");
						
						String newRow[] = new String[6];
						
						while(rs.next()) {
							newRow[0] = rs.getString(1);
							newRow[1] = rs.getString(2);
							newRow[2] = rs.getString(3);
							newRow[3] = rs.getString(4);
							newRow[4] = rs.getString(5);
							newRow[5] = rs.getString(6);
							
							model.addRow(newRow);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(t2.equals("")) {
					try {
						model.setNumRows(0);
						
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where point>='"+t1.getText()+"'");
						
						String newRow[] = new String[6];
						
						while(rs.next()) {
							newRow[0] = rs.getString(1);
							newRow[1] = rs.getString(2);
							newRow[2] = rs.getString(3);
							newRow[3] = rs.getString(4);
							newRow[4] = rs.getString(5);
							newRow[5] = rs.getString(6);
							
							model.addRow(newRow);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						model.setNumRows(0);
						
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where point>='"+t1.getText()+"' and point<='"+t2.getText()+"'");
						
						String newRow[] = new String[6];
						
						while(rs.next()) {
							newRow[0] = rs.getString(1);
							newRow[1] = rs.getString(3);
							newRow[2] = rs.getString(4);
							newRow[3] = rs.getString(5);
							newRow[4] = rs.getString(6);
							newRow[5] = rs.getString(7);
							
							model.addRow(newRow);
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
}
