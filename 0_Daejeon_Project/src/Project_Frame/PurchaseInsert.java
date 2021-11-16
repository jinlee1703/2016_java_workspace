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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Project_DBInterface.DBInterface;

public class PurchaseInsert extends JFrame {
	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	JTextField t3 = new JTextField();
	JTextField t4 = new JTextField();
	JTextField t5 = new JTextField();
	JSpinner spin = spin = new JSpinner();
	JRadioButton[] rb = new JRadioButton[7];
	JCheckBox cb = new JCheckBox("회원 정보와 동일");
	JLabel price = new JLabel();
	JButton btn = new JButton("주문하기");
	String pn;
	
	public PurchaseInsert(String pn) {
		this.pn = pn;
		setSize(400, 500);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel(new GridLayout(3, 1, 5, 5));
		p1.setBorder(new EmptyBorder(0, 0, 0, 5));
		p1.add(new JLabel("상품명"));
		p1.add(new JLabel("개수"));
		p1.add(new JLabel("사이즈"));
		
		JPanel p2 = new JPanel(new GridLayout(3, 1, 5, 5));
		JPanel rbp = new JPanel();
		p2.add(t1);
		p2.add(spin);
		p2.add(rbp);
		
		JPanel p3 = new JPanel(new GridLayout(5, 1, 5, 5));
		p3.setBorder(new EmptyBorder(0, 0, 0, 5));
		p3.add(new JLabel("주문하시는 분"));
		p3.add(new JLabel("받으시는 분"));
		p3.add(new JLabel("주소"));
		p3.add(new JLabel("배송 메세지"));
		p3.add(new JLabel());
		
		JPanel p4 = new JPanel(new GridLayout(5, 1, 5, 5));
		JPanel cbp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cbp.add(cb);
		p4.add(t2);
		p4.add(t3);
		p4.add(t4);
		p4.add(t5);
		p4.add(cbp);
		
		JPanel p5 = new JPanel();
		p5.setBorder(new EmptyBorder(0, 0, 0, 20));
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
		p5.add(new JLabel("총 결제 금액"));
		p5.add(price);
		
		//
		
		JPanel p6 = new JPanel(new BorderLayout());
		p6.setBorder(BorderFactory.createTitledBorder("주문하시는 상품"));
		p6.add(p1, BorderLayout.WEST);
		p6.add(p2, BorderLayout.CENTER);
		
		JPanel p7 = new JPanel(new BorderLayout());
		p7.setBorder(BorderFactory.createTitledBorder("주문고객/배송지정보"));
		p7.add(p3, BorderLayout.WEST);
		p7.add(p4, BorderLayout.CENTER);
		
		JPanel p8 = new JPanel(new BorderLayout());
		p8.setBorder(new EmptyBorder(20, 5, 10, 5));
		p8.add(p5, BorderLayout.WEST);
		p8.add(btn, BorderLayout.CENTER);
		btn.setPreferredSize(new Dimension(100, 40));
		
		p.add(p6);
		p.add(p7);
		p.add(p8);
		
		add(p);
		
		spin.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		
		t1.setText(pn);
		t1.setEditable(false);
		
		ButtonGroup g = new ButtonGroup();
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+pn+"'");
			if(rs.next()) {
				String sp[] = rs.getString(5).split(",");
				for(int i=0; i<sp.length; i++) {
					rb[i] = new JRadioButton(sp[i]);
					rb[i].setActionCommand(rb[i].getText());
					rbp.add(rb[i]);
					g.add(rb[i]);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		spinChange();
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			
			if(rs.next()) {
				t2.setText(rs.getString(3));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		spin.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				spinChange();
			}
		});
		
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cb.isSelected()) {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
						if(rs.next()) {
							t3.setText(rs.getString(3));
							t4.setText(rs.getString(5));
							
							t3.setEditable(false);
							t5.setEditable(false);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					t3.setText("");
					t4.setText("");
					
					t3.setEditable(true);
					t5.setEditable(true);
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t3.getText().equals("") || t4.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "입력 정보를 확인해주세요.");
				}
				
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+pn+"'");
					rs.next();
					
					DBInterface.Stmt.execute("INSERT INTO `project000`.`purchase` (`recipient`, `size`, `amount`, `cost`, `address`, `order_date`, `delivery`, `product_id`, `member_id`) "
							+ "VALUES ('"+t2.getText()+"', '"+g.getSelection().getActionCommand()+"', '"+spin.getValue().toString()+"', '"+rs.getInt(4)*Integer.parseInt(spin.getValue().toString())+"', '"+t4.getText()+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', '0', '"+rs.getString(1)+"', '"+Login.id.getText()+"');");
					
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void spinChange() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+pn+"'");
			if(rs.next()) {
				price.setText(new DecimalFormat("#,##0").format(rs.getInt(4)*Integer.parseInt(spin.getValue().toString())));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
