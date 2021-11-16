package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {
	JPanel[] pp = new JPanel[6];
	JButton[] btn = new JButton[6];
	JLabel[] l = new JLabel[6];
	String[] ln = {"가계부 달력보기","가계부 입력","수입&지출 조회","수입&지출 차트","회원정보관리","종료하기"};
	
	public Main() {
		setTitle("메인");
		setSize(550, 400);
		setLocationRelativeTo(null);
		
		btn[0] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("MainButton/달력.png")));
		btn[1] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("MainButton/입력.png")));
		btn[2] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("MainButton/내역.png")));
		btn[3] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("MainButton/차트.png")));
		btn[4] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("MainButton/회원정보.png")));
		btn[5] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("MainButton/종료.png")));
		
		JPanel p = new JPanel(new GridLayout(2, 3));
		p.setBorder(new EmptyBorder(20, 0, 20, 0));
		for(int i=0; i<6; i++) {
			pp[i] = new JPanel(new BorderLayout());
			btn[i].setBackground(Color.white);
			btn[i].setBorderPainted(false);
			btn[i].setOpaque(false);
			btn[i].addActionListener(this);
			l[i] = new JLabel(ln[i]);
			l[i].setHorizontalAlignment(l[i].CENTER);
			
			pp[i].add(btn[i], BorderLayout.CENTER);
			pp[i].add(l[i], BorderLayout.SOUTH);
			
			p.add(pp[i]);
		}
		
		add(p);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			this.dispose();
			new CalendarView();
		} else if(bt==btn[1]) {
			this.dispose();
			new LedgerInsert();
		} else if(bt==btn[2]) {
			this.dispose();
			new Suzy();
		} else if(bt==btn[3]) {
			new Chart();
		} else if(bt==btn[4]) {
			new MemberUpdate();
		} else if(bt==btn[5]) {
			System.exit(0);
		}
	}
}
