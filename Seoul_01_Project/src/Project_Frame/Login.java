package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Project_DBInterface.DBInterface;

public class Login extends JFrame {
	JLabel label1 = new JLabel("ID :");
	JLabel label2 = new JLabel("pw :");
	static JTextField text1 = new JTextField(14);
	JTextField text2 = new JPasswordField(14);
	JButton btn = new JButton("Login");
	
	public Login() {
		setTitle("로그인");
		setSize(200, 103);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 1));
		label1.setPreferredSize(new Dimension(30, 20));
		label2.setPreferredSize(new Dimension(30, 20));
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(btn, BorderLayout.CENTER);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
		add(basePanel);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+text1.getText()+"' and uPW='"+text2.getText()+"'");
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, rs.getString(4)+"님 환영합니다.");
						dispose();
						
						new Client();
					} else {
						JOptionPane.showMessageDialog(null, "ID 또는 PW오류");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
