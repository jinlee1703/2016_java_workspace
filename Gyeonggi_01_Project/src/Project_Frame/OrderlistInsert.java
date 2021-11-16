package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class OrderlistInsert extends JFrame implements ActionListener {
	JLabel title = new JLabel("상품주문");
	JLabel[] label = new JLabel[5];
	String[] ln = {"주문일자","ID","상품","수량","결제방법"};
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JComboBox combo1 = new JComboBox();
	JComboBox combo2 = new JComboBox();
	JButton btn1 = new JButton("주문");
	JButton btn2 = new JButton("취소");
	
	public OrderlistInsert(String id) {
		setTitle("상품주문");
		setSize(450, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(80, 20));
		}
		
		text1.setPreferredSize(new Dimension(335, 22));
		text2.setPreferredSize(new Dimension(335, 22));
		text3.setPreferredSize(new Dimension(335, 22));
		combo1.setPreferredSize(new Dimension(335, 22));
		combo2.setPreferredSize(new Dimension(335, 22));
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setBorder(new EmptyBorder(20, 0, 20, 0));
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 20));
		p2.add(label[0]); p2.add(text1);
		p2.add(label[1]); p2.add(text2);
		p2.add(label[2]); p2.add(combo1);
		p2.add(label[3]); p2.add(text3);
		p2.add(label[4]); p2.add(combo2);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
		p3.setBorder(new EmptyBorder(0, 0, 30, 0));
		btn1.setPreferredSize(new Dimension(100, 30));
		btn2.setPreferredSize(new Dimension(100, 30));
		p3.add(btn1); p3.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(p2, BorderLayout.CENTER);
		basePanel.add(p3, BorderLayout.SOUTH);
		add(basePanel);
		
		Init(id);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(combo1.getSelectedItem().toString().equals("") || text3.getText().equals("") || combo2.getSelectedItem().toString().equals("")) {
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
			}
			
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+combo1.getSelectedItem().toString()+"'");
				rs.next(); String pn = rs.getString(1);
				
				DBInterface.Stmt.execute("INSERT INTO `project000`.`orderlist` (`orderdate`, `memberid`, `productid`, `quantity`, `pay`) VALUES ('"+text1.getText()+"', '"+text2.getText()+"', '"+pn+"', '"+text3.getText()+"', '"+combo2.getSelectedItem().toString()+"');");
				
				JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
	
	public void Init(String id) {
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		text1.setText(s.format(d));
		text2.setText(id);
		text1.setEnabled(false);
		text2.setEnabled(false);
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product");
			
			while(rs.next()) {
				combo1.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		combo2.addItem("카드");
		combo2.addItem("현금");
	}
}
