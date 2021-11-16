package Project_Frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class OrderlistSelect extends JFrame {
	String[] header = {"주문일자","ID","상품","수량","결제방범"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public OrderlistSelect(String id) {
		setTitle("주문내역");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.add(scroll, BorderLayout.CENTER);
		add(basePanel);
		
		Init(id);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init(String id) {
		try {
			ResultSet rs= DBInterface.Stmt.executeQuery("select orderlist.orderdate,orderlist.memberid,product.name,orderlist.quantity,orderlist.pay from orderlist,product where product.id=orderlist.productid and memberid='"+id+"'");
			
			String[] newRow = new String[5];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(4);
				newRow[4] = rs.getString(5);
				
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
	}
}
