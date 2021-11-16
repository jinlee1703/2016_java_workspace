package Project_Frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class purchase extends JFrame {
	JLabel[] l = new JLabel[9];
	String[] ln = {"상품명","개수","사이즈","주문하시는 분","받으시는 분","주소","배송 메세지","","총 결제 금액"};
	JTextField[] t = new JTextField[5];
	JSpinner spin;
	JPanel rbp = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
	JPanel chp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	JRadioButton[] rb;
	JCheckBox cb = new JCheckBox("회원 정보와 동일");
	DecimalFormat df = new DecimalFormat("#,##0");
	JLabel price = new JLabel();
	JButton btn = new JButton("주문하기");
	ButtonGroup g;
	int pri;
	
	public purchase() {
		setSize(450, 550);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		Setting();
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setBorder(BorderFactory.createTitledBorder("주문하시는 상품"));
		p1.setPreferredSize(new Dimension(440, 140));
		l[0].setPreferredSize(new Dimension(50, 20)); t[0].setPreferredSize(new Dimension(340, 40));
		l[1].setPreferredSize(new Dimension(50, 20)); spin.setPreferredSize(new Dimension(340, 40));
		l[2].setPreferredSize(new Dimension(50, 20)); rbp.setPreferredSize(new Dimension(340, 40));
		p1.add(l[0]); p1.add(t[0]);
		p1.add(l[1]); p1.add(spin);
		p1.add(l[2]); p1.add(rbp);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setBorder(BorderFactory.createTitledBorder("주문고객/배송지정보"));
		p2.setPreferredSize(new Dimension(440, 230));
		l[3].setPreferredSize(new Dimension(100, 20)); t[1].setPreferredSize(new Dimension(290, 40));
		l[4].setPreferredSize(new Dimension(100, 20)); t[2].setPreferredSize(new Dimension(290, 40));
		l[5].setPreferredSize(new Dimension(100, 20)); t[3].setPreferredSize(new Dimension(290, 40));
		l[6].setPreferredSize(new Dimension(100, 20)); t[4].setPreferredSize(new Dimension(290, 40));
		l[7].setPreferredSize(new Dimension(100, 20)); chp.setPreferredSize(new Dimension(290, 40));
		p2.add(l[3]); p2.add(t[1]);
		p2.add(l[4]); p2.add(t[2]);
		p2.add(l[5]); p2.add(t[3]);
		p2.add(l[6]); p2.add(t[4]);
		p2.add(l[7]); p2.add(chp);
		chp.add(cb);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.setBorder(new EmptyBorder(10, 0, -10, 0));
		p3.setPreferredSize(new Dimension(440, 50));
		JPanel p31 = new JPanel();
		p31.setLayout(new BoxLayout(p31, BoxLayout.Y_AXIS));
		l[8].setPreferredSize(new Dimension(120, 20));
		price.setPreferredSize(new Dimension(120, 20));
		p31.add(l[8]);
		p31.add(price);
		btn.setPreferredSize(new Dimension(270, 40));
		p3.add(p31); p3.add(btn);
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		add(p);
		
		spin.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+t[0].getText()+"'");
					rs.next();
					pri = rs.getInt(4)*Integer.parseInt(spin.getValue().toString());
					price.setText(df.format(pri));
					//price.setText();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cc();
			}
		});
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t[2].getText().equals("") || t[3].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "입력 정보를 확인해주세요.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+t[0].getText()+"'");
						rs.next();
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						DBInterface.Stmt.execute("INSERT INTO `project000`.`purchase` (`recipient`, `size`, `amount`, `cost`, `address`, `order_date`, `delivery`, `product_id`, `member_id`) "
								+ "VALUES ('"+t[2].getText()+"', '"+g.getSelection().getActionCommand()+"', '"+spin.getValue()+"', '"+pri+"', '"+t[3].getText()+"', '"+sdf.format(new Date())+"', '0', '"+rs.getString(1)+"', '"+Login.id.getText()+"');");
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
			for(int i=0; i<ln.length; i++) {
				l[i] = new JLabel(ln[i]);
			}
			
			for(int i=0; i<5; i++) {
				t[i] = new JTextField();
			}
			
			t[0].setText(Products.proname);
			t[0].setEditable(false);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product where name='"+Products.proname+"'");
			if(rs.next()) {
				spin = new JSpinner(new SpinnerNumberModel(1, 1, rs.getInt(6), 1));
				pri = rs.getInt(4);
				price.setText(df.format(rs.getInt(4)));
				price.setFont(new Font("arial", Font.BOLD, 13));
				
				String[] sp = rs.getString(5).split(",");
				
				g = new ButtonGroup();
				
				rb = new JRadioButton[sp.length];
				
				for(int i=0; i<sp.length; i++) {
					rb[i] = new JRadioButton(sp[i]);
					rb[i].setActionCommand(sp[i]);
					g.add(rb[i]);
					rbp.add(rb[i]);
				}
				
				rb[0].setSelected(true);
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			rs.next();
			t[1].setText(rs.getString(3));
			t[1].setEditable(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cc() {
		if(cb.isSelected()) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
				rs.next();
				t[2].setText(rs.getString(3));
				t[3].setText(rs.getString(5));
				t[2].setEditable(false);
				t[3].setEditable(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			t[2].setText("");
			t[3].setText("");
			t[2].setEditable(true);
			t[3].setEditable(true);
		}
	}
}
