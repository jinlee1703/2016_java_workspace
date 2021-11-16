package Project_Frame;

import java.awt.BorderLayout;
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

public class Login extends JFrame {
	JLabel label1 = new JLabel("ID");
	JLabel label2 = new JLabel("��й�ȣ");
	static JTextField text1 = new JTextField(12);
	JTextField text2 = new JTextField(12);
	JButton btn1 = new JButton("Ȯ��");
	JButton btn2 = new JButton("���");
	
	public Login(int type) {
		setTitle("�α���");
		setSize(250, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(35, 0, 30, 10));
		
		JPanel p1 = new JPanel(new GridLayout(2, 2, 8, 15));
		p1.setBorder(new EmptyBorder(0, -80, 0, 20));
		label1.setHorizontalAlignment(label1.RIGHT);
		label2.setHorizontalAlignment(label2.RIGHT);
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		p2.add(btn1); p2.add(btn2);
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(type==0) {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where userID='"+text1.getText()+"' and password='"+text2.getText()+"'");
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "�α����� �Ϸ�Ǿ����ϴ�");
							dispose();
							new UserMenu();
						} else {
							JOptionPane.showMessageDialog(null, "���̵� �ٽ� �Է����ּ���.");
							text1.setText("");
							text2.setText("");
							label1.transferFocus();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(type==1) {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from student where studentID='"+text1.getText()+"' and password='"+text2.getText()+"'");
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "�α����� �Ϸ�Ǿ����ϴ�");
							dispose();
							new StudentMenu();
						} else {
							JOptionPane.showMessageDialog(null, "���̵� �ٽ� �Է����ּ���.");
							text1.setText("");
							text2.setText("");
							label1.transferFocus();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
