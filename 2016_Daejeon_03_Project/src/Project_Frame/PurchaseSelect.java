package Project_Frame;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class PurchaseSelect extends JFrame {
	String[] header = {"주문자","상품명","수령자","사이즈","주문일자","총액",""};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public PurchaseSelect() {
		setTitle("거래내역");
		setSize(700, 400);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		p.add(scroll);
		
		add(p);
		
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("SELECT purchase.id,member.name,product.name,recipient,purchase.size,order_date,cost FROM purchase join product join member on product_id=product.id and member_id=member.id and member_id='"+Login.id.getText()+"'");
			
			String[] newRow = new String[7];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(3);
				newRow[3] = rs.getString(2);
				newRow[4] = rs.getString(2);
				newRow[5] = rs.getString(2);
				newRow[6] = rs.getString(2);
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount()==2) {
					new PurchaseDelete((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
