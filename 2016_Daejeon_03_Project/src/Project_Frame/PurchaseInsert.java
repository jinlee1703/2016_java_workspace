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
	JSpinner spin = new JSpinner();
	JCheckBox cb = new JCheckBox("회원 정보와 동일");
	JLabel price = new JLabel();
	JButton btn = new JButton("주문하기");
	String proname;
	
	public PurchaseInsert(String proname) {
		setSize(420, 500);
		setLocationRelativeTo(null);
		this.proname=proname;
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(BorderFactory.createTitledBorder("주문하시는 상품"));
		
		JPanel p11 = new JPanel(new GridLayout(3, 1));
		p11.setBorder(new EmptyBorder(0, 0, 0, 5));
		p11.add(new JLabel("상품명"));
		p11.add(new JLabel("개수"));
		p11.add(new JLabel("사이즈"));
		
		JPanel p12 = new JPanel(new GridLayout(3, 1, 5, 5));
		spin.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		JPanel p121 = new JPanel();
		p12.add(t1);
		p12.add(spin);
		p12.add(p121);
		
		p1.add(p11, BorderLayout.WEST);
		p1.add(p12, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(BorderFactory.createTitledBorder("주문고객/배송지정보"));
		
		JPanel p21 = new JPanel(new GridLayout(5, 1));
		p21.setBorder(new EmptyBorder(0, 0, 0, 5));
		p21.add(new JLabel("주문하시는 분"));
		p21.add(new JLabel("받으시는 분"));
		p21.add(new JLabel("주소"));
		p21.add(new JLabel("배송 메세지"));
		
		JPanel p22 = new JPanel(new GridLayout(5, 1, 5, 5));
		JPanel p221 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p221.add(cb);
		p22.add(t2);
		p22.add(t3);
		p22.add(t4);
		p22.add(t5);
		p22.add(p221);
		
		p2.add(p21, BorderLayout.WEST);
		p2.add(p22, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel();
		p3.setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel p31 = new JPanel(new GridLayout(2, 1, 5, 5));
		p31.setBorder(new EmptyBorder(0, 0, 0, 50));
		p31.add(new JLabel("총 결제 금액"));
		p31.add(price);
		
		btn.setPreferredSize(new Dimension(250, 40));
		
		p3.add(p31); p3.add(btn);
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		add(p);
		
		t1.setText(proname);
		t1.setEditable(false);
		
		ButtonGroup g = new ButtonGroup();
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			rs.next();
			t2.setText(rs.getString(3));
			t2.setEditable(false);
			
			rs = DBInterface.Stmt.executeQuery("select * from product where name='"+proname+"'");
			rs.next();
			String sp[] = rs.getString(5).split(",");
			JRadioButton[] r = new JRadioButton[sp.length];
			for(int i=0; i<sp.length; i++) {
				r[i] = new JRadioButton(sp[i]);
				r[i].setActionCommand(sp[i]);
				g.add(r[i]);
				p121.add(r[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		spinChange();
		
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
						rs.next();
						
						t3.setText(rs.getString(3));
						t4.setText(rs.getString(5));
						
						t3.setEditable(false);
						t4.setEditable(false);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					t3.setText("");
					t4.setText("");
					
					t3.setEditable(true);
					t4.setEditable(true);
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t3.equals("") || t4.equals("")) {
					JOptionPane.showMessageDialog(null, "입력 정보를 확인해주세요.");
					return;
				}
				
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+proname+"'");
					rs.next();
					
					DBInterface.Stmt.execute("INSERT INTO `project000`.`purchase` (`recipient`, `size`, `amount`, `address`, `order_date`, `delivery`, `product_id`, `member_id`) "
							+ "VALUES ('"+t3.getText()+"', '"+g.getSelection().getActionCommand()+"', '"+spin.getValue().toString()+"', '"+t4.getText()+"', '날짜', '"+new SimpleDateFormat("yyyy-mm-dd").format(new Date())+"', '"+rs.getInt(1)+"', '"+Login.id.getText()+"');");
					
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
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+proname+"'");
			rs.next();
			DecimalFormat df = new DecimalFormat("#,##0");
			price.setText(df.format(Integer.parseInt(spin.getValue().toString())*rs.getInt(4)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
