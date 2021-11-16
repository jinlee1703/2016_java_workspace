package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class employeeSelect extends JFrame implements ActionListener {
	static String[] header = {"사번","이름","Email","연락처"};
	static DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton[] btn = new JButton[3];
	String[] bn = {"추가","수정","삭제"};
	
	public employeeSelect() {
		setTitle("사원목록");
		setSize(450, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(scroll, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setBorder(new EmptyBorder(0, 0, 10, 0));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			btn[i].setPreferredSize(new Dimension(80, 30));
			p2.add(btn[i]);
		}
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
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
			new employeeInsert();
		} else if(table.getSelectedRow()==-1) {
			
		} else if(bt==btn[1]) {
			
		} else if(bt==btn[2]) {
			
		}
	}
	
	public static void Init() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from employee");
			
			String[] newRow = new String[4];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(3);
				newRow[2] = rs.getString(4);
				newRow[3] = rs.getString(5);
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
//1