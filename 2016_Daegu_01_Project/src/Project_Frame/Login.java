package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("ID :");
	JLabel label2 = new JLabel("PW :");
	static JTextField idText = new JTextField(10);
	JTextField pwText = new JPasswordField(10);
	JButton btn1 = new JButton("로그인");
	JButton btn2 = new JButton("회원가입");
	JLabel img = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("DataFiles/Image1.jpg")));
	
	
	public Login() {
		setTitle("RiceCake Store");
		setSize(700, 630);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(label1); p1.add(idText); p1.add(label2); p1.add(pwText); p1.add(btn1); p1.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		JPanel p2= new JPanel(new BorderLayout());
		p2.setToolTipText("무지개떡");
		p2.add(img);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(p2, BorderLayout.CENTER);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			if(idText.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
				idText.requestFocus();
				
			} else if(pwText.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
				pwText.requestFocus();
			}
			if(idText.getText().equals("admin") && pwText.getText().equals("1234")) {
				JOptionPane.showMessageDialog(null, "관리자 아이디로 로그인합니다.", "로그인", JOptionPane.INFORMATION_MESSAGE);
				new Main2();
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where id='"+idText.getText()+"' and pw='"+pwText.getText()+"'");
					
					if(idText.getText().equals("") || pwText.equals("")) {
					} else {
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, rs.getString(4)+"님 환영합니다.", "로그인", JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
							new Main1();
						} else {
							JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 다시 확인바랍니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(btn==btn2) {
			new Join();
		}
	}
}
