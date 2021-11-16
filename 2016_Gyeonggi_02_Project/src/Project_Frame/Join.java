package Project_Frame;

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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Join extends JFrame {
	JLabel title = new JLabel("회원가입 폼");
	JPanel[] pp = new JPanel[6];
	JLabel[] l = new JLabel[6];
	String[] ln = {"회원 아이디","회원 비밀번호","이름","주소","이메일","자동방지문자"};
	JTextField[] t = new JTextField[6];
	JButton btn1 = new JButton("가입");
	JButton btn2 = new JButton("취소");
	JLabel c1 = new JLabel("");
	JLabel c2 = new JLabel("");
	String rd = String.format("%04d", new Random().nextInt(10000));
	
	public Join() {
		setTitle("회원가입");
		setSize(400, 360);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.setFont(new Font("HY견고딕", Font.PLAIN, 25));
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		t[0] = new JTextField(10);
		t[1] = new JTextField(10);
		t[2] = new JTextField(5);
		t[3] = new JTextField(20);
		t[4] = new JTextField(20);
		t[5] = new JTextField(5);
		for(int i=0; i<6; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pp[i].setPreferredSize(new Dimension(350, 30));
			l[i] = new JLabel(ln[i]+" :");
			l[i].setPreferredSize(new Dimension(100, 20));
			pp[i].add(l[i]);
			pp[i].add(t[i]);
			p2.add(pp[i]);
		}
		c2.setText("문자: "+rd);
		c2.setForeground(Color.red);
		pp[1].add(c1); pp[5].add(c2);
		
		JPanel p3 = new JPanel();
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rd.equals(t[5].getText())) {
					for(int i=0; i<5; i++) {
						if(t[i].getText().equals("")) {
							return;
						}
					}
					
					try {
						dispose();
						DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `address`, `email`) "
								+ "VALUES ('"+t[0].getText()+"', '"+t[1].getText()+"', '"+t[2].getText()+"', '"+t[3].getText()+"', '"+t[4].getText()+"');");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "오류메시지", "오류", JOptionPane.ERROR_MESSAGE);
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
		
		t[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(t[1].getText().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
					c1.setText("(취약함)");
					c1.setForeground(Color.red);
				} else {
					c1.setText("(안전함)");
					c1.setForeground(Color.blue);
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
