package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {
	JButton[] btn = new JButton[3];
	String[] bn = {"사원목록","업무일지","데이터 가져오기"};
	
	public Main() {
		setTitle("Main");
		setSize(520, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new FlowLayout());
		p.setBorder(new EmptyBorder(80, 0, 0, 0));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			btn[i].setPreferredSize(new Dimension(150, 50));
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
			new employeeSelect();
		} else if(bt==btn[1]) {
			
		} else if(bt==btn[2]) {
			
		} 
	}
}
