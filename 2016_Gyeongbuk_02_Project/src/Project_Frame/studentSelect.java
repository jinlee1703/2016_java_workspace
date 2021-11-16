package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class studentSelect extends JFrame implements ActionListener {
	JLabel[] l = new JLabel[4];
	String[] ln = {"학번","이름","연락처","학과"};
	JTextField[] t = new JTextField[3];
	JComboBox combo = new JComboBox();
	JButton[] btn = new JButton[3];
	String[] bn = {"추가","삭제","초기화"};
	String[] header = {"학번","이름","연락처","학과"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public studentSelect() {
		setTitle("학사관리");
		setSize(500, 480);
		setLocationRelativeTo(null);
		
		new MenuBar(this);
		
		Init();
		TableSetting();
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel(new GridLayout(4, 2, 5, 1));
		p1.setBorder(BorderFactory.createTitledBorder("학생 정보"));
		for(int i=0; i<3; i++) {
			p1.add(l[i]);
			p1.add(t[i]);
		}
		p1.add(l[3]); p1.add(combo);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(btn[0]); p2.add(btn[1]); p2.add(btn[2]);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.add(scroll);
		
		JPanel p4 = new JPanel(new BorderLayout());
		p4.add(p1, BorderLayout.CENTER); p4.add(p2, BorderLayout.SOUTH);
		
		p.add(p4);
		p.add(p3);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			
		} else if(bt==btn[0]) {
			
		} else {
			t[0].setText("");
			t[1].setText("");
			t[2].setText("###-####-####");
			combo.setSelectedIndex(-1);
			table.clearSelection();
		}
	}
	
	public void Init() {
		for(int i=0; i<4; i++) {
			l[i] = new JLabel(ln[i]);
		}
		
		for(int i=0; i<3; i++) {
			t[i] = new JTextField();
		}
		t[0].setEditable(false);
		t[2].setText("###-####-####");
		t[2].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if(t[2].getText().length()>=13) {
					t[2].setText(t[2].getText().substring(0, 13));
				} else {
					if(t[2].getText().length()!=5) {
						if(e.getKeyCode()==96 || e.getKeyCode()==97 || e.getKeyCode()==98 || e.getKeyCode()==99 || e.getKeyCode()==100 || e.getKeyCode()==101 || e.getKeyCode()==102 || e.getKeyCode()==103 || e.getKeyCode()==104 || e.getKeyCode()==105 || e.getKeyCode()==8) {
							if(t[2].getText().length()==3) {
								t[2].setText(t[2].getText()+"-");
							} else if(t[2].getText().length()==8) {
								t[2].setText(t[2].getText()+"-");
							}
						} else {
							t[2].setText(t[2].getText().substring(0, t[2].getText().length()-1));
						}
					} else {
						if(e.getKeyCode()==96 || e.getKeyCode()==97 || e.getKeyCode()==98 || e.getKeyCode()==99 || e.getKeyCode()==100 || e.getKeyCode()==101 || e.getKeyCode()==102 || e.getKeyCode()==103 || e.getKeyCode()==104 || e.getKeyCode()==105 || e.getKeyCode()==32 || e.getKeyCode()==8) {
						} else {
							t[2].setText(t[2].getText().substring(0, t[2].getText().length()-1));
						}
					}
				}
			}
		});
		
		for(int i=0; i<3; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
		}
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from department");
			
			while(rs.next()) {
				combo.addItem(rs.getString(2));
			}
			
			combo.setSelectedIndex(-1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for(int i=0; i<4; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(center);
		}
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btn[1].setEnabled(false);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void TableSetting() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("SELECT stdno,stdname,student.tel,deptname FROM student join department on student.deptno=department.deptno;");
			
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
	}
}

/*
첼시 파브레가스
첼시 아자르
유벤투스 마르키시오
유벤투스 보누치
인테르 이카르디
*/