package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

import Project_DBInterface.DBInterface;

public class Join extends JFrame {
	JLabel title = new JLabel("모든 정보를 빠집없이 입력해주세요.", JLabel.CENTER);
	JPanel[] pp = new JPanel[7];
	JLabel[] l = new JLabel[7];
	String rd = String.format("%02d", new Random().nextInt(1000000));
	String[] ln = {"아이디","비밀번호","비밀번호 확인","이름","연락처","주소",rd};
	JTextField t1 = new JTextField(18);
	JPasswordField t2 = new JPasswordField(18);
	JPasswordField t3 = new JPasswordField(18);
	JTextField t4 = new JTextField(18);
	JTextField t5 = new JTextField(18);
	JTextField t6 = new JTextField(25);
	JTextField t7 = new JTextField(18);
	JLabel cl1 = new JLabel(); int c1=0;
	JLabel cl2 = new JLabel(); int c2=0;
	JLabel cl3 = new JLabel(); int c3=0;
	JButton btn1 = new JButton("가입");
	JButton btn2 = new JButton("취소");
	
	public Join() {
		setTitle("아이디");
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.black);
		title.setForeground(Color.white);
		p1.add(title);
		
		JPanel p2 = new JPanel(new GridLayout(7, 1));
		for(int i=0; i<7; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(80, 30));
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
		l[6].setFont(new Font("맑은 고딕", Font.BOLD, 15));
		
		JPanel p3 = new JPanel();
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		t1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				cl1.setText("아이디를 입력해주세요.");
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
			}
		});
		
		t3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(t2.getText().equals(t3.getText())) {
					cl2.setText("비밀번호가 일치합니다");
					c2=1;
				} else {
					cl2.setText("비밀번호가 일치하지 않습니다.");
					c2=0;
				}
			}
		});
		
		t7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(rd.equals(t7.getText())) {
					cl3.setText("일치합니다.");
					c3=1;
				} else {
					cl3.setText("일치하지 않습니다.");
					c3=0;
				}
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t1.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				if(t2.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				if(t3.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				if(t4.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				if(t5.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				if(t6.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				if(t7.getText().equals("")) {JOptionPane.showMessageDialog(null, "모든 정보를 입력햊쉐요."); return;}
				
				if(c1==0 || c2==0 || c3==0) {
					JOptionPane.showMessageDialog(null, "가입정보를 확인해주세요.");
					return;
				}
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `nname`, `phone_num`, `address`, `point`) VALUES ('"+t1.getText()+"', '"+t2.getText()+"', '"+t4.getText()+"', '"+t5.getText()+"', '"+t6.getText()+"', '0');");
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
