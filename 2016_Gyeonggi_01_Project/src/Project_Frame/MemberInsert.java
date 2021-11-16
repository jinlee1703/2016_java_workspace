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

import Project_DBInterface.DBInterface;

public class MemberInsert extends JFrame implements ActionListener {
	JLabel title = new JLabel("회원가입 폼");
	JLabel[] label = new JLabel[6];
	String[] ln = {"회원 아이디 :","회원 비밀번호 :  ","이름 :","주소 :","이메일 :","자동방지문자 :  "};
	JTextField[] text = new JTextField[6];
	JLabel cl = new JLabel("(취약함)");
	JLabel rl = new JLabel("문자: ");
	JButton btn1 = new JButton("가입");
	JButton btn2 = new JButton("취소");
	String a;
	
	public MemberInsert() {
		setTitle("회원가입");
		setSize(400, 320);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		title.setFont(new Font("HY견고딕", Font.PLAIN, 28));
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		JPanel[] pp = new JPanel[6];
		text[0] = new JTextField(10);
		text[1] = new JTextField(10);
		text[2] = new JTextField(5);
		text[3] = new JTextField(20);
		text[4] = new JTextField(20);
		text[5] = new JTextField(5);
		for(int i=0; i<6; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pp[i].setPreferredSize(new Dimension(350, 30));
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(100, 20));
			pp[i].add(label[i]);
			pp[i].add(text[i]);
			p2.add(pp[i]);
		}
		pp[1].add(cl);
		pp[5].add(rl);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		Init();
		
		text[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(text[1].getText().equals("")) {
					cl.setText("");
				} else {
					if(text[1].getText().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
						cl.setText("(취약함)");
						cl.setForeground(Color.red);
					} else {
						cl.setText("(안전함)");
						cl.setForeground(Color.blue);
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			if(a.equals(text[5].getText())) {
				JOptionPane.showMessageDialog(null, "자동방지문자 오류", "오류", JOptionPane.ERROR_MESSAGE);
			} else {
				if(text[0].getText().equals("") || text[1].getText().equals("") || text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("")) {
				} else {
					try {
						DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `address`, `email`) "
								+ "VALUES ('"+text[0].getText()+"', '"+text[1].getText()+"', '"+text[2].getText()+"', '"+text[3].getText()+"', '"+text[4].getText()+"');");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} else {
			this.dispose();
		}
	}
	
	public void	Init() {
		cl.setText("");
		Random rd = new Random();
		a = Integer.toString(rd.nextInt(10000));
		rl.setText("문자: "+a);
		cl.setForeground(Color.red);
		rl.setForeground(Color.red);
	}
}
