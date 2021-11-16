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
	JLabel title = new JLabel("�α���");
	JLabel label1 = new JLabel("���̵�");
	JLabel label2 = new JLabel("��й�ȣ");
	static JTextField text1 = new JTextField(10);
	JTextField text2 = new JTextField(10);
	JButton btn1 = new JButton("�α���");
	JButton btn2 = new JButton("�ݱ�");
	
	public Login() {
		setTitle("�α���");
		setSize(300, 200);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		title.setFont(new Font("���� ���", Font.PLAIN, 20));
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
				JOptionPane.showMessageDialog(null, "�����ڸ��");
				this.dispose();
				
				
			} else {
				if(text1.getText().equals("") || text2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� �Է����ּ���.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from protector where ID='"+text1.getText()+"' and PW='"+text2.getText()+"'");
						
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, rs.getString(4)+" �� �α����ϼ̽��ϴ�.");
							this.dispose();
							new AdminSelect();
						} else {
							JOptionPane.showMessageDialog(null, "���������ʴ� ���̵��Դϴ�.");
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
