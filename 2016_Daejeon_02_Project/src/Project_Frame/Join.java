package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Join extends JFrame implements ActionListener {
	JLabel title = new JLabel("모든 정보를 빠짐없이 입력해주세요.");
	JLabel[] l = new JLabel[7];
	String rd = String.format("%06d", new Random().nextInt(1000000));
	String[] ln = {"아이디","비밀번호","비밀번호 확인","이름","연락처","주소",rd};
	JTextField t1 = new JTextField(20);
	JTextField t2 = new JTextField(20);
	JPasswordField t3 = new JPasswordField(20);
	JTextField t4 = new JTextField(20);
	JTextField t5 = new JTextField(20);
	JTextField t6 = new JTextField(28);
	JTextField t7 = new JTextField(20);
	JLabel cl1 = new JLabel();
	JLabel cl2 = new JLabel();
	JLabel cl3 = new JLabel();
	JButton btn1 = new JButton("가입");
	JButton btn2 = new JButton("취소");
	int c1, c2, c3;
	
	public Join() {
		setTitle("회원가입");
		setSize(500, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.black);
		title.setForeground(Color.white);
		title.setHorizontalAlignment(title.CENTER);
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		p2.setBorder(new EmptyBorder(10, 5, 0, 0));
		JPanel[] pp = new JPanel[7];
		for(int i=0; i<7; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(100, 25));
			pp[i].add(l[i]);
			p2.add(pp[i]);
		}
		pp[0].add(t1); pp[0].add(cl1);
		pp[1].add(t2);
		pp[2].add(t3); pp[2].add(cl2);
		pp[3].add(t4);
		pp[4].add(t5);
		pp[5].add(t6);
		pp[6].add(t7); pp[6].add(cl3);
		l[6].setFont(new Font("맑은 고딕", Font.BOLD, 20));
		l[6].setBorder(new EmptyBorder(5, 0, 0, 0));
		t3.setEchoChar('●');
		
		JPanel p3 = new JPanel();
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		setFocusable(true);
		
		t1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				cl1.setText("아이디를 입력해주세요");
				c1=0;
			}
		});
		
		t1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+t1.getText()+"'");
					if(rs.next()) {
						cl1.setText("중복된 아이디 입니다.");
						c1=0;
					} else {
						cl1.setText("사용 가능한 아이디 입니다.");
						c1=1;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		t3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				cl2.setText("비밀번호를 입력해주세요.");
				c2=0;
			}
		});
		
		t3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(! t2.getText().equals(t3.getText())) {
					cl2.setText("비밀번호가 일치하지 않습니다.");
					c2=0;
				} else {
					cl2.setText("비밀번호가 일치합니다");
					c2=1;
				}
			}
		});
		
		t7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(! t7.getText().equals(rd)) {
					cl3.setText("일치하지 않습니다.");
					c3=0;
				} else {
					cl3.setText("일치합니다.");
					c3=1;
				}
			}
		});
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "모든 정보를 입력해주세요.");
			} else if(c1==0 || c2==0 || c3==0) {
				JOptionPane.showMessageDialog(null, "가입 정보를 확인해주세요.");
			} else {
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `phone_num`, `address`) VALUES ('"+t1.getText()+"', '"+t2.getText()+"', '"+t4.getText()+"', '"+t5.getText()+"', '"+t6.getText()+"');");
					this.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			this.dispose();
		}
	}
}
