package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener {	
	JLabel title = new JLabel("로그인");
	JLabel label1 = new JLabel("아이디");
	JLabel label2 = new JLabel("비밀번호");
	static JTextField text1 = new JTextField(10);
	JTextField text2 = new JTextField(10);
	JButton btn1 = new JButton("로그인");
	JButton btn2 = new JButton("닫기");
	
	public Login() {
		setTitle("로그인");
		setSize(300, 200);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		title.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout());
		label1.setPreferredSize(new Dimension(80, 20));
		label2.setPreferredSize(new Dimension(80, 20));
		p2.add(label1); p2.add(text1);
		p2.add(label2); p2.add(text2);
		
		JPanel p3 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(text1.getText().equals("admin") && text2.getText().equals("admin")) {
				JOptionPane.showMessageDialog(null, "관리자모드");
				this.dispose();
				
				
			} else {
				if(text1.getText().equals("") || text2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력해주세요.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from protector where ID='"+text1.getText()+"' and PW='"+text2.getText()+"'");
						
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, rs.getString(4)+" 님 로그인하셨습니다.");
							this.dispose();
							new AdminSelect();
						} else {
							JOptionPane.showMessageDialog(null, "존재하지않는 아이디입니다.");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} else {
			System.exit(0);
		}
	}
}
