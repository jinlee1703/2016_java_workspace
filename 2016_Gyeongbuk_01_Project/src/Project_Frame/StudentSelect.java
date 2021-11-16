package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class StudentSelect extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("학번");
	JLabel label2 = new JLabel("이름");
	JLabel label3 = new JLabel("연락처");
	JLabel label4 = new JLabel("학과");
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JComboBox combo = new JComboBox();
	JButton btn1 = new JButton("추가");
	JButton btn2 = new JButton("삭제");
	JButton btn3 = new JButton("초기화");
	String[] header = {"학번","이름","연락처","학과"}; 
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public StudentSelect() {
		setTitle("학사관리");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		new MenuBar(this);
		
		JPanel p = new JPanel(new FlowLayout());
		
		JPanel p1 = new JPanel(new GridLayout(4, 2, 5, 1));
		p1.setPreferredSize(new Dimension(495, 150));
		p1.setBorder(BorderFactory.createTitledBorder("학생 정보"));
		label1.setHorizontalAlignment(label1.RIGHT);
		label2.setHorizontalAlignment(label2.RIGHT);
		label3.setHorizontalAlignment(label3.RIGHT);
		label4.setHorizontalAlignment(label4.RIGHT);
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		p1.add(label3); p1.add(text3);
		p1.add(label4); p1.add(combo);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setPreferredSize(new Dimension(500, 33));
		p2.add(btn1); p2.add(btn2); p2.add(btn3);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setPreferredSize(new Dimension(495, 250));
		p3.setBorder(BorderFactory.createTitledBorder("학생 목록"));
		p3.add(scroll);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				text1.setText((String) table.getValueAt(table.getSelectedRow(), 0));
				text2.setText((String) table.getValueAt(table.getSelectedRow(), 1));
				text3.setText((String) table.getValueAt(table.getSelectedRow(), 2));
				combo.setSelectedItem((String) table.getValueAt(table.getSelectedRow(), 3));
				
				btn1.setText("수정");
			}
		});
		
		p.add(p1); p.add(p2); p.add(p3);
		
		add(p);
		
		InitSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
			
		if(btn==btn1) {
			if(text1.getText().equals("") || text2.getText().equals("") || text3.getText().equals("") || combo.getSelectedItem().toString().equals("")) {
				JOptionPane.showMessageDialog(null, "공백 존재", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				if(btn1.getText()=="추가") {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from department where deptname='"+combo.getSelectedItem().toString()+"'");
						rs.next(); String a = rs.getString(1);
						DBInterface.Stmt.execute("INSERT INTO `haksa_000`.`student` (`stdno`, `stdname`, `tel`, `deptno`) "
								+ "VALUES ('"+text1.getText()+"', '"+text2.getText()+"', '"+text3.getText()+"', '"+a+"');");
						
						JOptionPane.showMessageDialog(null, text2.getText()+"("+text1.getText()+") 학생이 추가되었습니다.", "학생 추가", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {				
					try {
						DBInterface.Stmt.execute("UPDATE `haksa_000`.`student` SET `stdname`='"+text2.getText()+"', `tel`='"+text3.getText()+"' WHERE `stdno`='"+text1.getText()+"';");
						
						JOptionPane.showMessageDialog(null, text2.getText()+"("+text1.getText()+") 학생이 수정되었습니다.", "학생 수정", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} else if(btn==btn2) {
			try {
				DBInterface.Stmt.execute("DELETE FROM `haksa_000`.`student` WHERE `stdno`='"+text1.getText()+"';");
				
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from course where stdno='"+text1.getText()+"'");
				
				while(rs.next()) {
					DBInterface.Stmt.execute("DELETE FROM `haksa_000`.`course` WHERE `stdno`='"+text1.getText()+"';");
				}
				
				JOptionPane.showMessageDialog(null, text2.getText()+"("+text1.getText()+") 학생이 삭제되었습니다.", "학생 삭제", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(btn==btn3) {
			text1.setText("");
			text2.setText("");
			text3.setText("###-####-####");
			combo.setSelectedIndex(-1);
			
			btn1.setText("추가");
			table.isRowSelected(-1);
		}
	}
	
	public void InitSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from department");
			
			while(rs.next()) {
				combo.addItem(rs.getString(2));
			}
			combo.setSelectedIndex(-1);
			combo.addActionListener(new ComboChange());
			
			rs = DBInterface.Stmt.executeQuery("select student.stdno,student.stdname,student.tel,department.deptname from student join department on student.deptno=department.deptno;");
			
			String[] newRow = new String[4];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				
				model.addRow(newRow);
			}
			
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			table.getColumnModel().getColumn(0).setCellRenderer(center);
			table.getColumnModel().getColumn(1).setCellRenderer(center);
			table.getColumnModel().getColumn(2).setCellRenderer(center);
			table.getColumnModel().getColumn(3).setCellRenderer(center);
			
			text1.setEnabled(false);
			btn2.setEnabled(false);
			
			text3.setText("###-####-####");
			text3.addKeyListener(new TelChange());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ComboChange implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Date d = new Date(); SimpleDateFormat s = new SimpleDateFormat("yyyy");
				String a = s.format(d);
				
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from department where deptname='"+combo.getSelectedItem().toString()+"'");
				rs.next();
				String b = String.format("%03d", rs.getInt(1));
				
				rs = DBInterface.Stmt.executeQuery("select count(*) from student"); rs.next();
				String c = String.format("%03d", rs.getInt(1)+1);
				text1.setText(a+b+c);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class TelChange implements KeyListener {
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(text3.getText().length()>=13) {
				text3.setText(text3.getText().substring(0, 13));
			} else {
				if(text3.getText().length()!=5) {
					if(e.getKeyCode()==96 || e.getKeyCode()==97 || e.getKeyCode()==98 || e.getKeyCode()==99 || e.getKeyCode()==100 || e.getKeyCode()==101 || e.getKeyCode()==102 || e.getKeyCode()==103 || e.getKeyCode()==104 || e.getKeyCode()==105 || e.getKeyCode()==8) {
						if(text3.getText().length()==3) {
							text3.setText(text3.getText()+"-");
						} else if(text3.getText().length()==8) {
							text3.setText(text3.getText()+"-");
						}
					} else {
						text3.setText(text3.getText().substring(0, text3.getText().length()-1));
					}
				} else {
					if(e.getKeyCode()==96 || e.getKeyCode()==97 || e.getKeyCode()==98 || e.getKeyCode()==99 || e.getKeyCode()==100 || e.getKeyCode()==101 || e.getKeyCode()==102 || e.getKeyCode()==103 || e.getKeyCode()==104 || e.getKeyCode()==105 || e.getKeyCode()==32 || e.getKeyCode()==8) {
					} else {
						text3.setText(text3.getText().substring(0, text3.getText().length()-1));
					}
				}
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
