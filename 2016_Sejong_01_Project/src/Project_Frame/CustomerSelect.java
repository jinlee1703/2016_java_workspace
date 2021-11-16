package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class CustomerSelect extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[4];
	String[] ln = {"고객번호 :","고  객  명 :","생년월일 :","가입일자 :"};
	JTextField[] text = new JTextField[4];
	JButton[] btn = new JButton[5];
	String[] bn = {"추가","저장","수정","삭제","닫기"};
	String[] header = {"고객번호","고객명","생년월일","가입일자"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public CustomerSelect() {
		setTitle("고객관리");
		setSize(500, 410);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new FlowLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
		p1.setBorder(new EmptyBorder(-10, 100, 0, 100));
		p1.setPreferredSize(new Dimension(500, 120));
		for(int i=0; i<4; i++) {
			label[i] = new JLabel(ln[i]);
			text[i] = new JTextField(16);
			p1.add(label[i]);
			p1.add(text[i]);
		}
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setPreferredSize(new Dimension(500, 40));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			p2.add(btn[i]);
		}
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setPreferredSize(new Dimension(500, 250));
		p3.add(scroll);
		
		p.add(p1); p.add(p2); p.add(p3);
		add(p);
		
		TableSetting();
		
		text[2].addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(text[1].getText().equals("") || text[2].getText().equals("")) {
					btn[1].setEnabled(false);
				} else {
					btn[1].setEnabled(true);
				}
			}
		});
		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				text[0].setText((String) table.getValueAt(table.getSelectedRow(), 0));
				text[1].setText((String) table.getValueAt(table.getSelectedRow(), 1));
				text[2].setText((String) table.getValueAt(table.getSelectedRow(), 2));
				text[3].setText((String) table.getValueAt(table.getSelectedRow(), 3));
				
				btn[2].setEnabled(true);
				btn[3].setEnabled(true);
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			try {
				text[0].setEnabled(false);
				text[1].setEnabled(true);
				text[2].setEnabled(true);
				text[3].setEnabled(false);
				
				ResultSet rs = DBInterface.Stmt.executeQuery("select count(*) from customer");
				rs.next(); String a = Integer.toString(rs.getInt(1)+1);
				text[0].setText(a);
				
				Date d = new Date();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				text[3].setText(s.format(d));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} if(bt==btn[1]) {
			try {
				if(text[1].getText().equals("") || text[2].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "빈칸 없이 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
				} else {
					DBInterface.Stmt.execute("INSERT INTO `hair`.`customer` (`고객번호`, `고객명`, `생년월일`, `가입일자`) "
							+ "VALUES ('"+text[0].getText()+"', '"+text[1].getText()+"', '"+text[2].getText()+"', '"+text[3].getText()+"');");
					
					JOptionPane.showMessageDialog(null, "입력이 완료되었습니다.");
					
					btn[1].setEnabled(false);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} if(bt==btn[2]) {
			
		} if(bt==btn[3]) {
			
		} if(bt==btn[4]) {
			this.dispose();
		}
	}
	
	public void TableSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from customer");
			
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
