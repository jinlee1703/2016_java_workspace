package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener {
	JLabel title = new JLabel("영화예매프로그램");
	JLabel img = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Data/Intro.jpg")));
	JLabel label1 = new JLabel("ID :");
	JLabel label2 = new JLabel("PW :");
	JTextField id = new JTextField(10);
	JTextField pw = new JPasswordField(10);
	JButton btn1 = new JButton("로그인");
	JButton btn2 = new JButton("회원가입");
	JButton btn3 = new JButton("종료");
	
	public Login() {
		setTitle("영화예매로그인");
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(30, 20, 50, 10));
		
		JPanel pt = new JPanel(new FlowLayout());
		title.setFont(new Font("굴림", Font.BOLD, 30));
		pt.add(title);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(img);
		
		JPanel p2 = new JPanel(null);
		p2.setPreferredSize(new Dimension(250,100));
		label1.setBounds(5, 90, 30, 20);
		label2.setBounds(5, 120, 30, 20);
		id.setBounds(35, 88, 120, 24);
		pw.setBounds(35, 118, 120, 24);
		btn1.setBounds(160, 88, 80, 54);
		btn2.setBounds(20, 150, 90, 30);
		btn3.setBounds(120, 150, 90, 30);
		
		p2.add(label1); p2.add(label2);
		p2.add(id);	p2.add(pw);
		p2.add(btn1); p2.add(btn2); p2.add(btn3);
		
		p.add(pt, BorderLayout.NORTH);
		p.add(p1, BorderLayout.WEST);
		p.add(p2, BorderLayout.EAST);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			} else if(pw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from customer where id='"+id.getText()+"'");
					
					if(rs.next()) {
						rs = DBInterface.Stmt.executeQuery("select * from customer where id='"+id.getText()+"' and pw='"+pw.getText()+"'");
						
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
							this.dispose();
							
							new Program();
						} else {
							JOptionPane.showMessageDialog(null, "일치하지 않는 비밀번호 입니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다.", "오류", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		} else if(bt==btn2) {
			new CustomerInsert();
		} else if(bt==btn3) {
			System.exit(0);
		}
	}
}
