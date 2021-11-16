package Project_Frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class purchaseInsert extends JFrame {
	JLabel[] label = new JLabel[7];
	String[] ln = {"상품명","개수","사이즈","주문하시는 분","받으시는 분","주소","배송 메세지"};
	JTextField[] text = new JTextField[6];
	JSpinner spin;
	JRadioButton rb1 = new JRadioButton("S");
	JRadioButton rb2 = new JRadioButton("M");
	JRadioButton rb3 = new JRadioButton("L");
	JRadioButton rb4 = new JRadioButton("XL");
	JCheckBox check = new JCheckBox("회원 정보와 동일");
	JLabel sum = new JLabel();
	JButton btn = new JButton("주문하기");
	String size;
	
	public purchaseInsert(String productName) {
		setSize(430, 550);
		setLocationRelativeTo(null);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+productName+"'");
			if(rs.next()) {
				SpinnerNumberModel model = new SpinnerNumberModel(1, 1, rs.getInt(6), 1);
				spin = new JSpinner(model);
				
				rb1.setVisible(false);
				rb2.setVisible(false);
				rb3.setVisible(false);
				rb4.setVisible(false);
				
				if(rs.getString(3).equals("상의")) {
					if(rs.getString(5).contains("S")) {
						rb1.setVisible(true);
						size = "S";
					}
					if(rs.getString(5).contains("M")) {
						rb2.setVisible(true);
						size = "M";
					}
					if(rs.getString(5).contains("L")) {
						rb3.setVisible(true);
						size = "L";
					}
					if(rs.getString(5).contains("XL")) {
						rb4.setVisible(true);
						size = "XL";
					}
				} else {
					if(rs.getString(5).contains("44")) {
						rb1.setText("44");
						rb1.setVisible(true);
						size = "44";
					}
					if(rs.getString(5).contains("46")) {
						rb2.setText("46");
						rb2.setVisible(true);
						size = "46";
					}
					if(rs.getString(5).contains("48")) {
						rb3.setText("48");
						rb3.setVisible(true);
						size = "48";
					}
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<7; i++) {
			label[i] = new JLabel(ln[i]);
		}
		
		for(int i=0; i<6; i++) {
			text[i] = new JTextField();
		}
		
		text[0].setText(productName);
		text[1].setText(Login.id.getText());
		text[0].setEditable(false);
		text[1].setEditable(false);
		
		JPanel p = new JPanel(new FlowLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setPreferredSize(new Dimension(410, 160));
		p1.setBorder(BorderFactory.createTitledBorder("주문하시는 상품"));
		for(int i=0; i<3; i++) {
			label[i].setPreferredSize(new Dimension(40, 40));
		}
		JPanel pp1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		pp1.setPreferredSize(new Dimension(350, 40));
		text[0].setPreferredSize(new Dimension(350, 40));
		spin.setPreferredSize(new Dimension(350, 40));
		pp1.setBorder(new EmptyBorder(7, 0, 0, 0));
		ButtonGroup g1 = new ButtonGroup();
		g1.add(rb1); g1.add(rb2); g1.add(rb3); g1.add(rb4);
		pp1.add(rb1); pp1.add(rb2); pp1.add(rb3); pp1.add(rb4);
		
		p1.add(label[0]); p1.add(text[0]);
		p1.add(label[1]); p1.add(spin);
		p1.add(label[2]); p1.add(pp1);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setPreferredSize(new Dimension(410, 250));
		p2.setBorder(BorderFactory.createTitledBorder("주문고객/배송지정보"));
		for(int i=3; i<7; i++) {
			label[i].setPreferredSize(new Dimension(90, 40));
			text[i-2].setPreferredSize(new Dimension(300, 40));
			p2.add(label[i]);
			p2.add(text[i-2]);
		}
		check.setBorder(new EmptyBorder(0, 0, 0, 0));
		p2.add(check);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
		p3.setPreferredSize(new Dimension(410, 50));
		sumPrice(productName);
		btn.setPreferredSize(new Dimension(250, 40));
		p3.add(sum); p3.add(btn);
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		add(p);
		
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(check.isSelected()==true) {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
						
						if(rs.next()) {
							text[2].setText(rs.getString(3));
							text[3].setText(rs.getString(5));
							text[2].setEditable(false);
							text[3].setEditable(false);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					text[2].setText("");
					text[3].setText("");
					text[2].setEditable(true);
					text[3].setEditable(true);
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rb1.isSelected()==true || rb2.isSelected()==true || rb3.isSelected()==true || rb4.isSelected()==true) {
					JOptionPane.showMessageDialog(null, "입력 정보를 확인해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
				} else {
					if(text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "입력 정보를 확인해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+productName+"'");
							rs.next();
							int a = Integer.parseInt(spin.getValue().toString());
							int su = rs.getInt(4)*a;
							
							Date d = new Date();
							SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
							
							DBInterface.Stmt.execute("INSERT INTO `project000`.`purchase` (`recipient`, `size`, `amount`, `cost`, `address`, `order_date`, `delivery`, `product_id`, `member_id`) "
									+ "VALUES ('"+text[2].getText()+"', '"+size+"', '"+spin.getValue().toString()+"', '"+su+"', '"+text[3].getText()+"', '"+sd.format(d)+"', 'T,F', '"+rs.getString(1)+"', '"+Login.id.getText()+"');");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void sumPrice(String productName) {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+productName+"'");
			rs.next();
			int a = Integer.parseInt(spin.getValue().toString());
			int su = rs.getInt(4)*a;
			DecimalFormat df = new DecimalFormat("#,##0");
			sum.setText("<html>총 결제 금액<br>"+df.format(su)+"</html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 