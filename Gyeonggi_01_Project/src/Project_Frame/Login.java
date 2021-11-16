package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[2];
	String[] ln = {"아이디","비밀번호"};
	JTextField[] text = new JTextField[2];
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	
	public Login() {
		setTitle("고객 로그인");
		setSize(280, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		for(int i=0; i<2; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(80, 20));
			label[i].setHorizontalAlignment(label[i].LEFT);
			text[i] = new JTextField(9);
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(20, 20, 50, 61));
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(label[0]); p1.add(text[0]);
		p1.add(label[1]); p1.add(text[1]);
		
		JPanel p2 = new JPanel(new GridLayout(1, 2, 10, 0));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		p2.add(btn1); p2.add(btn2);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(text[0].getText().equals("") || text[1].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+text[0].getText()+"' and password='"+text[1].getText()+"'");
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, rs.getString(2)+"님 환영합니다.");
						new Member(text[0].getText());
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "아이디,비밀번호를 잘못 입력하셨습니다.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "아이디,비밀번호를 잘못 입력하셨습니다.");
				}
			}
		} else {
			this.dispose();
		}
	}
}
