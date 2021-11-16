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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class MemberSelect extends JFrame {
	String[] header = {"ID","성명","생년월일","주소","PassWord"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JLabel label = new JLabel("전체회원 수:");
	JTextField text = new JTextField(10);
	
	public MemberSelect() {
		setTitle("전체 고객 조회");
		setSize(500, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(80, 0, 0, 0));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.setBorder(new EmptyBorder(20, 0, 40, 30));
		p1.add(label); p1.add(text);
		
		basePanel.add(scroll, BorderLayout.CENTER);
		basePanel.add(p1, BorderLayout.SOUTH);
		add(basePanel);
		
		Init();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member order by id asc");
			
			String[] newRow = new String[5];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				newRow[4] = rs.getString(5);
				
				model.addRow(newRow);
			}
			
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			DefaultTableCellRenderer right = new DefaultTableCellRenderer();
			right.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
			for(int i=0; i<4; i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(center);
			}
			table.getColumnModel().getColumn(4).setCellRenderer(right);
			
			rs = DBInterface.Stmt.executeQuery("select count(*) from member");
			rs.next(); text.setText(rs.getString(1)+"명");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
