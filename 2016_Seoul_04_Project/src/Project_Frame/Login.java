package Project_Frame;

import java.awt.BorderLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame {
	static JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	JButton btn = new JButton("Login");
	
	public Login() {
		setTitle("로그인");
		setSize(300, 150);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(2, 1, 1, 1));
		p1.setBorder(new EmptyBorder(2, 2, 2, 2));
		p1.add(new JLabel("ID :"));
		p1.add(new JLabel("PW :"));
		
		JPanel p2 = new JPanel(new GridLayout(2, 1, 1, 1));
		p2.setBorder(new EmptyBorder(2, 2, 2, 2));
		p2.add(id);
		p2.add(pw);
		
		p.add(p1, BorderLayout.WEST);
		p.add(p2, BorderLayout.EAST);
		p.add(btn, BorderLayout.SOUTH);
		
		add(p);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+id.getText()+"' and uPW='"+pw.getText()+"'");
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, rs.getString(4)+"님 환영합니다.");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "ID 또는 PW를 확인해주세요.", "", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
