package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class SalesInsert extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("상품명");
	JLabel label2 = new JLabel("수량");
	JComboBox combo1 = new JComboBox();
	JComboBox combo2 = new JComboBox();
	JButton btn1 = new JButton("주문");
	JButton btn2 = new JButton("취소");
	
	public SalesInsert() {
		setTitle("상품 주문");
		setSize(200, 140);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(2, 2, 5, 5));
		p1.setBorder(new EmptyBorder(10, 10, 10, 10));
		label1.setHorizontalAlignment(label1.RIGHT);
		label2.setHorizontalAlignment(label2.RIGHT);
		p1.add(label1); p1.add(combo1);
		p1.add(label2); p1.add(combo2);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from ricecake");
			
			while(rs.next()) {
				combo1.addItem(rs.getString(2));
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from ricecake where name='"+combo1.getSelectedItem().toString()+"'");
			rs.next();
			
			for(int i=1; i<rs.getInt(3)+1; i++) {
				combo2.addItem(i);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		combo1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					combo2.removeAllItems();
					
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from ricecake where name='"+combo1.getSelectedItem().toString()+"'");
					rs.next();
					
					for(int i=1; i<rs.getInt(3)+1; i++) {
						combo2.addItem(i);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(btn1); p2.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ee) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) ee.getSource();
		
		if(btn==btn1) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select count(*) from sales");
				rs.next(); int a = rs.getInt(1);
				
				Date dd = new Date();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				String b = s.format(dd);
				
				rs = DBInterface.Stmt.executeQuery("select * from ricecake where name='"+combo1.getSelectedItem().toString()+"'");
				rs.next(); int c = rs.getInt(1);
				
				String d = combo2.getSelectedItem().toString();
				
				rs = DBInterface.Stmt.executeQuery("select * from client where id='"+Login.idText.getText()+"'");
				rs.next(); int e = rs.getInt(1);
				
				DBInterface.Stmt.execute("INSERT INTO `world000`.`sales` (`code`, `salesdate`, `fk_ricecakecode`, `amount`, `fk_clientcode`) "
						+ "VALUES ('"+a+"', '"+b+"', '"+c+"', '"+d+"', '"+e+"');");
				
				JOptionPane.showMessageDialog(null, "주문 성공");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
}
