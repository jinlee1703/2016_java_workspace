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
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame {
	JLabel label1 = new JLabel("ID");
	JLabel label2 = new JLabel("PW");
	static JTextField text1 = new JTextField(12);
	JTextField text2 = new JPasswordField(12);
	JButton btn = new JButton("Login");
	
	public Login() {
		setTitle("Login");
		setSize(380, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(50, 40, 50, 30));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		//p1.setBorder(new LineBorder(Color.black));
		p1.setPreferredSize(new Dimension(200, 100));
		label1.setPreferredSize(new Dimension(50, 20));
		label2.setPreferredSize(new Dimension(50, 20));
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 10, 8, 0));
		p2.add(btn, BorderLayout.CENTER);
		
		basePanel.add(p1, BorderLayout.WEST);
		basePanel.add(p2, BorderLayout.CENTER);
		
		add(basePanel);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(text1.getText().equals("admin") && text2.getText().equals("admin")) { //�Ŵ��� �α���
					new Main();
					dispose();
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from employee where id='"+text1.getText()+"'");
						
						if(rs.next()) {
							rs = DBInterface.Stmt.executeQuery("select * from employee where id='"+text1.getText()+"' and pw='"+text2.getText()+"'");
							
							if(rs.next()) { //�Ϲ� �α���
								new LogSelect();
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "���̵� ���� ���� �ʽ��ϴ�.");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}