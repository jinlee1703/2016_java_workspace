package Project_Frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AdminMain extends JFrame implements ActionListener {
	JButton[] btn = new JButton[4];
	String[] bn = {"상품추가","회원조회","로그아웃"};
	
	public AdminMain() {
		setSize(250, 200);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		for(int i=0; i<3; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			p.add(btn[i]);
		}
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new ProductInsert();
		} else if(bt==btn[1]) {
			new Members();
		} else if(bt==btn[2]) {
			this.dispose();
			new Login();
		}
	}
}
