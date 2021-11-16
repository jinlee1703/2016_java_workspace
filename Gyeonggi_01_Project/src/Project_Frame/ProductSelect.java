package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class ProductSelect extends JFrame {
	JLabel[] label = new JLabel[2];
	String[] ln = {"제조업체","상품명"};
	JComboBox[] combo = new JComboBox[2];
	JButton btn = new JButton("검색");
	String[] header = {"제조업체","상품명","가격"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public ProductSelect() {
		setTitle("상품조회");
		setSize(600, 410);
		setResizable(false);
		setLocationRelativeTo(null);
		
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(80, 20));
			combo[i] = new JComboBox();
			combo[i].setPreferredSize(new Dimension(150, 22));
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(80, 20, 20, 20));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		p1.add(label[0]); p1.add(combo[0]);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		btn.setPreferredSize(new Dimension(80, 22));
		p2.add(label[1]); p2.add(combo[1]); p2.add(btn);
		
		JPanel p3 = new JPanel(new GridLayout(2,1));
		p3.add(p1); p3.add(p2);
		
		p3.setBorder(new EmptyBorder(0, 0, 15, 0));
		
		basePanel.add(p3, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);
		add(basePanel);
		
		Setting();
		TableInsert();
		
		combo[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from supplier where name='"+combo[0].getSelectedItem().toString()+"'");
					rs.next(); String num = rs.getString(1);
					
					rs = DBInterface.Stmt.executeQuery("select `product`.`name` from supplier join product on `supplier`.`ID`=`product`.`supplierid` where supplierid='"+num+"'");
					
					combo[1].removeAllItems();
					
					while(rs.next()) {
						combo[1].addItem(rs.getString(1));
					}
					combo[1].addItem("전체");
					
					combo[1].setSelectedIndex(-1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(combo[0].getSelectedItem().toString().equals("") && combo[1].getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "검색하실 조건을 선택해주세요.");
				} else {
					try {
						ResultSet  rs = DBInterface.Stmt.executeQuery("select * from supplier where name='"+combo[0].getSelectedItem().toString()+"'");
						rs.next(); String num = rs.getString(1);
						
						model.setNumRows(0);
						
						String p;
						
						if(combo[1].getSelectedIndex()==-1 || combo[1].getSelectedItem().toString().equals("전체")) {
							p = "%";
						} else {
							p = combo[1].getSelectedItem().toString();
						}
						
						rs = DBInterface.Stmt.executeQuery("select `supplier`.`name`,`product`.`name`,`product`.`amount` from supplier join product on `supplier`.`ID`=`product`.`supplierid` where supplierid='"+num+"' and `product`.`name` like '"+p+"'");
						
						String[] newRow = new String[3];
						DecimalFormat d = new DecimalFormat("#,##0");
						
						while(rs.next()) {
							newRow[0] = rs.getString(1);
							newRow[1] = rs.getString(2);
							newRow[2] = d.format(rs.getInt(3));
							
							model.addRow(newRow);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Setting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from supplier");
			
			while(rs.next()) {
				combo[0].addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		combo[0].setSelectedIndex(-1);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		right.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		for(int i=0; i<2; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(center);
		}
		table.getColumnModel().getColumn(2).setCellRenderer(right);
	}
	
	public void ComboChange() {
		
	}
	
	public void TableInsert() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select `supplier`.`name`,`product`.`name`,`product`.`amount` from supplier join product on `supplier`.`ID`=`product`.`supplierid`");
			
			String[] newRow = new String[3];
			DecimalFormat d = new DecimalFormat("#,##0");
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = d.format(rs.getInt(3));
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
