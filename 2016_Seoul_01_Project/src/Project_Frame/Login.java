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

public class Login extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("ID :");
	JLabel label2 = new JLabel("PW :");
	JTextField id = new JTextField(13);
	JTextField pw = new JPasswordField(13);
	JButton btn = new JButton("Login");
	
	public Login() {
		setTitle("로그인");
		setSize(205, 125);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		label1.setPreferredSize(new Dimension(30, 20));
		label2.setPreferredSize(new Dimension(30, 20));
		p1.add(label1); p1.add(id);
		p1.add(label2); p1.add(pw);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		
		btn.addActionListener(this);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+id.getText()+"' and uPW='"+pw.getText()+"'");
			
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, rs.getString(4)+"님 환영합니다.");
				this.dispose();
				new Main();
			} else {
				JOptionPane.showMessageDialog(null, "ID 또는 PW를 확인해주세요.", "", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
