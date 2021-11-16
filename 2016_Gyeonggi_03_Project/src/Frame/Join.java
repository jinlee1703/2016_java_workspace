package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	String[] ln = {"회원 아이디","회원 비밀번호","이름","주소","이메일","자동방지문자"};
	JLabel[] l = new JLabel[ln.length];
	JPanel[] pp = new JPanel[ln.length];
	int[] ts = {10, 10, 5, 20, 20, 5};
	JTextField[] t = new JTextField[ln.length];
	JLabel cl1 = new JLabel();
	String rd = String.format("%04d", new Random().nextInt(10000)); 
	JLabel cl2 = new JLabel("문자 : "+rd);
	JButton btn1 = new JButton("가입");
	JButton btn2 = new JButton("취소");
	
	public Join() {
		setTitle("회원가입");
		setSize(400, 400);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("회원가입 폼")).setFont(new Font("HY견고딕", Font.BOLD, 25));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for(int i=0; i<ln.length; i++) {
			l[i] = new JLabel(ln[i]+" :");
			l[i].setPreferredSize(new Dimension(100, 20));
			if(i==1) {
				t[i] = new JPasswordField(ts[i]);
			} else {
				t[i] = new JTextField(ts[i]);
			}
			
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			pp[i].add(l[i]);
			pp[i].add(t[i]);
			
			p2.add(pp[i]);
		}
		
		pp[1].add(cl1);
		pp[5].add(cl2);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		cl2.setForeground(Color.red);
		
		t[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
//				if(t[1].getText().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
//					cl1.setText("(취약함)");
//					cl1.setForeground(Color.red);
//				} else {
//					cl1.setText("(안전함)");
//					cl1.setForeground(Color.blue);
//				}
				
				
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0; i<6; i++) {
					if(t[i].getText().equals("")) {
						return;
					}
				}
				
				if(t[5].getText().equals(rd)) {
					JOptionPane.showMessageDialog(null, "오류");
					return;
				}
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `address`, `email`) VALUES ('"+t[0].getText()+"', '"+t[1].getText()+"', '"+t[2].getText()+"', '"+t[3].getText()+"', '"+t[4].getText()+"');");
					
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
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
