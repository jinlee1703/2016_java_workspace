package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class MemberSelect extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("포인트");
	JLabel label2 = new JLabel("~");
	JTextField text1 = new JTextField(5);
	JTextField text2 = new JTextField(5);
	JButton btn = new JButton("검색");
	String[] header = {"아이디","비밀번호","이름","연락처","주소","포인트"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public MemberSelect() {
		setTitle("고객조회");
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		btn.addActionListener(this);
		p1.add(label1); p1.add(text1); p1.add(label2); p1.add(text2); p1.add(btn);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(scroll);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		
		add(p);
		
		Init();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(text1.getText().equals("") && text2.getText().equals("")) {
			Init();
		} else {
			try {
				int a;
				int b;
				if(text1.getText().equals("")) {
					a = 0;
				} else {
					a = Integer.parseInt(text1.getText()); 
				}
				
				if(text2.getText().equals("")) {
					b = 0;
				} else {
					b = Integer.parseInt(text2.getText()); 
				}
				
				model.setNumRows(0);
				
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where point>="+a+" and point<="+b);
				
				String[] newRow = new String[6];
				
				while(rs.next()) {
					for(int i=0; i<6; i++) {
						newRow[i] = rs.getString(i+1);
					}
					
					model.addRow(newRow);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void Init() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member");
			
			String[] newRow = new String[6];
			
			while(rs.next()) {
				for(int i=0; i<6; i++) {
					newRow[i] = rs.getString(i+1);
				}
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
