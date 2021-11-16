package Project_Frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame implements ActionListener {
	String[] bn = {"사원관리","고객관리","통장관리","거래내역","종료"};
	JButton[] btn = new JButton[5];
	
	public Main() {
		setTitle("사원관리");
		setSize(460, 65);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].setBackground(Color.white);
			btn[i].addActionListener(this);
			add(btn[i]);
		}
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new sawonSelect();
		} else if(bt==btn[1]) {
			new customerSelect();
		} else if(bt==btn[2]) {
			
		} else if(bt==btn[3]) {
			
		} else {
			System.exit(0);;
		}
	}
}
