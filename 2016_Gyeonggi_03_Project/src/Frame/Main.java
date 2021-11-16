package Frame;

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
	String[] bn = {"달력","입력","내역","차트","회원정보","종료"};
	JButton[] btn = new JButton[6];
	String[] ln = {"가계부 달력보기","가계부 입력","수입&지출 조회","수입&지출 차트","회원정보관리","종료하기"};
	JLabel[] l = new JLabel[6];
	
	public Main() {
		setTitle("메인");
		setSize(600, 450);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(2, 3, 50, 50));
		p.setBorder(new EmptyBorder(50, 50, 50, 50));
		JPanel[] pp = new JPanel[6];
		for(int i=0; i<6; i++) {
			pp[i] = new JPanel(new BorderLayout());
			
			String path = System.getProperty("user.dir")+"\\res\\main\\"+bn[i]+".png";
			path.replace('\\', '/');
			
			btn[i] = new JButton(new ImageIcon(path));
			l[i] = new JLabel(ln[i]);
			l[i].setHorizontalAlignment(JLabel.CENTER);
			
			btn[i].setOpaque(false);
			btn[i].setBorderPainted(false);
			btn[i].setBackground(Color.white);
			btn[i].addActionListener(this);
			
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
		this.dispose();
		if(bt==btn[0]) {
			new CalendarView();
		} else if(bt==btn[1]) {
			new LedegerInsert();
		} else if(bt==btn[2]) {
			new Suzy();
		} else if(bt==btn[3]) {
			new Chart();
		} else if(bt==btn[4]) {
			new MemberSelect();
		} else {
			System.exit(0);
		}
	}
	
}
