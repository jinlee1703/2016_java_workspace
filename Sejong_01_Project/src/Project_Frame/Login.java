package Project_Frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("ID :");
	JLabel label2 = new JLabel("PW :");
	JTextField text1 = new JTextField(12);
	JTextField text2 = new JTextField(12);
	JButton btn1 = new JButton("로그인");
	JButton btn2 = new JButton("종료");
	
	public Login() {
		setTitle("은행 관리자 로그인");
		setSize(490, 70);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new FlowLayout());
		p.setBorder(new EmptyBorder(5, 0, 0, 0));
		
		p.add(label1);
		p.add(text1);
		p.add(label2);
		p.add(text2);
		p.add(btn1);
		p.add(btn2);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(text1.getText().equals("admin") && text2.getText().equals("1234")) {
				new Main();
				this.dispose();
			}
		} else {
			System.exit(0);
		}
	}
}
