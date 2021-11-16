package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {
	JLabel title = new JLabel("미용실관리프로그램");
	JButton[] btn = new JButton[6];
	String[] bn = {"고객관리","고객검색","헤어주문","헤어주문검색","주문현황차트","종료"};
	
	public Main() {
		setTitle("미용실관리프로그램");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setBorder(new EmptyBorder(20, 0, 0, 0));
		title.setFont(new Font("굴림", Font.BOLD, 30));
		p1.add(title);
		
		JPanel p2 = new JPanel(new GridLayout(6, 1, 2, 2));
		p2.setBorder(new EmptyBorder(20, 150, 50, 150));
		for(int i=0; i<6; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			btn[i].setFont(new Font("굴림", Font.BOLD, 18));
			p2.add(btn[i]);
		}
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new CustomerSelect();
		} else if(bt==btn[1]) {
			
		} else if(bt==btn[2]) {
			
		} else if(bt==btn[3]) {
			
		} else if(bt==btn[4]) {
			
		} else if(bt==btn[5]) {
			System.exit(0);
		}
	}
}
