package Project_Frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {
	JButton[] btn = new JButton[3];
	String[] bn = {"강사로그인","수강자로그인","전체수강자조회"};
	
	public Main() {
		setTitle("메인");
		setSize(230, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new GridLayout(3, 1, 15, 15));
		basePanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			basePanel.add(btn[i]);
		}
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new Login(0);
		} else if(bt==btn[1]) {
			new Login(1);
		} else if(bt==btn[2]) {
			new AllStudentSelect();
		}
	}
}
