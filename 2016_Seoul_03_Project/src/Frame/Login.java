package Frame;

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
	static JTextField id = new JTextField(12);
	JTextField pw = new JPasswordField(12);
	JButton btn = new JButton("Login");
	
	public Login() {
		setTitle("로그인");
		setSize(200, 115);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
		p1.add(new JLabel("ID :")).setPreferredSize(new Dimension(30, 20));
		p1.add(id);
		p1.add(new JLabel("PW :")).setPreferredSize(new Dimension(30, 20));
		p1.add(pw);
		
		p.add(p1, BorderLayout.CENTER);
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
						new Main();
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
