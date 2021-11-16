package Project_Frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class MemberOrderlist extends JFrame {
	String[] header = {"회원이름","상품명","판매수량","단가","판매금액"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public MemberOrderlist() {
		setTitle("고객별 주문 현황");
		setSize(500, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(scroll, BorderLayout.CENTER);
		
		add(p);
		
		Init();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select `member`.`name`,`product`.`name`,`orderlist`.`quantity`,format(`product`.`amount`,'#,##0'),format(`orderlist`.`quantity`*`product`.`amount`,'#,##0') from `member`,`orderlist`,`product` where `orderlist`.`memberid`=`member`.`id` and `orderlist`.`productid`=`product`.`id` group by `member`.`name` order by `orderlist`.`quantity`*`product`.`amount` desc");
			
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
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		
		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(1).setCellRenderer(center);
		table.getColumnModel().getColumn(2).setCellRenderer(center);
		table.getColumnModel().getColumn(3).setCellRenderer(right);
		table.getColumnModel().getColumn(4).setCellRenderer(right);
	}
}
