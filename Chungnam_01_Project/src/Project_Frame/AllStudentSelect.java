package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class AllStudentSelect extends JFrame {
	JLabel label = new JLabel("전체 수강자 목록");
	String[] header = {"ID","이름","성별","학과","학년"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public AllStudentSelect() {
		setTitle("수강신청자 목록");
		setSize(360, 278);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(20, 15, 30, 5));
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(new EmptyBorder(0, 20, 20, 0));
		p.add(label);
		
		basePanel.add(p, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);
		add(basePanel);
		
		Init();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from student");
			
			String[] newRow = new String[5];
			
			while(rs.next()) {
				newRow[0] = rs.getString(2);
				newRow[1] = rs.getString(3);
				newRow[2] = rs.getString(5);
				newRow[3] = rs.getString(6);
				newRow[4] = rs.getString(7);
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
