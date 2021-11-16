package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class ClientSalesSelect extends JFrame implements ActionListener {
	JButton btn1 = new JButton("텍스트파일로저장");
	JButton btn2 = new JButton("돌아가기");
	String[] header = {"주문번호","날짜","상품명","수량"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public ClientSalesSelect() {
		setTitle("주문이력조회");
		setSize(450, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		//setUndecorated(true);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(btn1); p1.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(BorderFactory.createTitledBorder("주문이력"));
		p2.add(scroll);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(p2, BorderLayout.CENTER);
		
		add(basePanel);
		
		TableSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			try {
				FileSave();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
	
	public void TableSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where id='"+Login.idText.getText()+"'");
			rs.next(); String a = rs.getString(1);
			
			rs = DBInterface.Stmt.executeQuery("SELECT sales.code,sales.salesdate,ricecake.name,sales.amount FROM sales join ricecake on ricecake.code=sales.fk_ricecakecode where fk_clientcode='"+a+"' order by sales.code desc;");
			
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
		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(1).setCellRenderer(center);
		table.getColumnModel().getColumn(2).setCellRenderer(center);
		table.getColumnModel().getColumn(3).setCellRenderer(center);
	}
	
	public void FileSave() throws Exception {
		FileDialog fd = new FileDialog(this, "텍스트파일로저장", FileDialog.SAVE);
		fd.setVisible(true);
		
		FileWriter fw = new FileWriter(fd.getDirectory()+fd.getFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where id='"+Login.idText.getText()+"'");
		rs.next(); String a = rs.getString(4);
		
		String str = a+"회원님의 "+s.format(d)+"까지의 구매이력";
		bw.write(str); bw.newLine(); bw.newLine();
		
		str = "번호\t주묹날짜\t떡이름\t수량";
		bw.write(str); bw.newLine();
		
		rs = DBInterface.Stmt.executeQuery("select * from client where id='"+Login.idText.getText()+"'");
		rs.next(); a = rs.getString(1);
		
		rs = DBInterface.Stmt.executeQuery("SELECT sales.code,sales.salesdate,ricecake.name,sales.amount FROM sales join ricecake on ricecake.code=sales.fk_ricecakecode where fk_clientcode='"+a+"' order by sales.code desc;");
		
		while(rs.next()) {
			bw.newLine();
			str = rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4);
			bw.write(str);
		}
		bw.close();
	}
}
