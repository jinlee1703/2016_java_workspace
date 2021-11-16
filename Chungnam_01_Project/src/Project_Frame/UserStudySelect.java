package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class UserStudySelect extends JFrame {
	String[] header = {"ID","이름","학과","학년"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JLabel label;
	
	public UserStudySelect() {
		setTitle("수강신청자 조회");
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where userID='"+Login.text1.getText()+"'");
			rs.next(); String sub = rs.getString(5); 
			
			label = new JLabel(sub + " 신청자 조회");
			
			rs = DBInterface.Stmt.executeQuery("select student.studentID,student.name,student.type,student.sin from student,study where student.id=study.studentID and study."+sub+"='1'");
			
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
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(20, 20, 50, 20));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.setBorder(new EmptyBorder(0, 20, 20, 0));
		p1.add(label);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);
		
		add(basePanel);	
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
