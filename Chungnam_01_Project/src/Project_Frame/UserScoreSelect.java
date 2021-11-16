package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class UserScoreSelect extends JFrame {
	JLabel label;
	String[] header = {"과목","점수","강사명"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton btn = new JButton("메뉴로");
	
	public UserScoreSelect() {
		setTitle("수강자 점수 조회");
		setSize(250, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(0, 20, 0, 8));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.setBorder(new EmptyBorder(20, 20, 20, 0));
		p1.add(label);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p2.setBorder(new EmptyBorder(10, 30, 2, 0));
		p2.add(btn);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		Init();
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from student where studentID='"+Login.text1.getText()+"'");
			rs.next(); String sName=rs.getString(3); String sID=rs.getString(1);
			
			label.setText(sName+"님의 점수");
			
			String[] newRow = new String[3];
			
			rs = DBInterface.Stmt.executeQuery("select * from study where studentid='"+sID+"' and word<>'0'");
			
			if(rs.next()) {
				newRow[0] = "word";
				newRow[0] = rs.getString(2);
				newRow[0] = sName;
				
				model.addRow(newRow);
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from study where studentid='"+sID+"' and java<>'0'");
			
			if(rs.next()) {
				newRow[0] = "java";
				newRow[0] = rs.getString(3);
				newRow[0] = sName;
				
				model.addRow(newRow);
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from study where studentid='"+sID+"' and excel<>'0'");
			
			if(rs.next()) {
				newRow[0] = "excel";
				newRow[0] = rs.getString(4);
				newRow[0] = sName;
				
				model.addRow(newRow);
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from study where studentid='"+sID+"' and ppt<>'0'");
			
			if(rs.next()) {
				newRow[0] = "ppt";
				newRow[0] = rs.getString(5);
				newRow[0] = sName;
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
