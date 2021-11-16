package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class sawonSelect extends JFrame implements ActionListener {
	String[] ln = {"사원코드","사원명","직 급","권 한"};
	JLabel[] label = new JLabel[4];
	String[] bn = {"추가","제거","수정","종료"};
	JButton[] btn = new JButton[4];
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	String[] cn1 = {"사원","대리","과장","부장","대표"};
	JComboBox combo1 = new JComboBox(cn1);
	String[] cn2 = {"A","B","C"};
	JComboBox combo2 = new JComboBox(cn2);
	String[] header = {"사원코드","사 원 명","직 급","권 한"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public sawonSelect() {
		setTitle("사원 관리 프로그램");
		setSize(950, 290);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		basePanel.setBorder(new EmptyBorder(-10, 0, 0, 0));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		p1.setPreferredSize(new Dimension(115, 250));
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setBorder(new BevelBorder(BevelBorder.RAISED));
			label[i].setFont(new Font("맑은 고딕", Font.BOLD, 18));
			label[i].setHorizontalAlignment(label[i].CENTER);
			label[i].setPreferredSize(new Dimension(115, 50));
			p1.add(label[i]);
		}
		
		JPanel p2 = new JPanel(new GridLayout(4, 1, 10, 10));
		p2.setBorder(new EmptyBorder(10, 0, 10, 0));
		p2.setPreferredSize(new Dimension(115, 250));
		p2.add(text1); text1.setPreferredSize(new Dimension(115, 50));
		p2.add(text2); text2.setPreferredSize(new Dimension(115, 50));
		p2.add(combo1); combo1.setBorder(new EmptyBorder(15, 30, 10, 30));
		p2.add(combo2); combo2.setBorder(new EmptyBorder(15, 40, 10, 40));
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		p3.setPreferredSize(new Dimension(115, 250));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].setFont(new Font("맑은 고딕", Font.BOLD, 18));
			btn[i].setPreferredSize(new Dimension(115, 50));
			btn[i].addActionListener(this);
			p3.add(btn[i]);
		}
		
		JPanel p4 = new JPanel(new BorderLayout());
		p4.setBorder(new EmptyBorder(17, 0, 0, 0));
		p4.setPreferredSize(new Dimension(550, 265));
		p4.add(scroll, BorderLayout.CENTER);
		
		basePanel.add(p1);
		basePanel.add(p2);
		basePanel.add(p3);
		basePanel.add(p4);
		add(basePanel);
		
		TableSetting();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				text1.setText((String) table.getValueAt(table.getSelectedRow(), 0));
				text2.setText((String) table.getValueAt(table.getSelectedRow(), 1));
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
			if(text1.getText().equals("") || text2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "입력요구");
			} else {
				try {
					DBInterface.Stmt.execute("INSERT INTO `skill000`.`sawon` (`사원코드`, `사원명`, `직급`, `권한`) "
							+ "VALUES ('"+text1.getText()+"', '"+text2.getText()+"', '"+combo1.getSelectedItem().toString()+"', '"+combo2.getSelectedItem().toString()+"');");
					
					TableSetting();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(bt==btn[1]) {
			try {
				DBInterface.Stmt.execute("DELETE FROM `skill000`.`sawon` WHERE `사원코드`='"+table.getValueAt(table.getSelectedRow(), 0)+"';");
				TableSetting();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn[2]) {
			try {
				DBInterface.Stmt.execute("UPDATE `skill000`.`sawon` SET `사원코드`='"+text1.getText()+"', `사원명`='"+text2.getText()+"', `직급`='"+combo1.getSelectedItem().toString()+"', `권한`='"+combo2.getSelectedItem().toString()+"' "
						+ "WHERE `사원코드`='"+table.getValueAt(table.getSelectedRow(), 0)+"';");
				TableSetting();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
	
	public void TableSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from sawon");
			
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
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for(int i=0; i<header.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(center);
		}
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
