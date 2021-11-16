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
	String[] bn = {"�޷�","�Է�","����","��Ʈ","ȸ������","����"};
	JButton[] btn = new JButton[6];
	JLabel[] l = new JLabel[6];
	String[] ln = {"����� �޷º���","����� �Է�","����&���� ��ȸ","����&���� ��Ʈ","ȸ����������","�����ϱ�"};
	
	public Main() {
		setTitle("����");
		setSize(600, 450);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(2, 3));
		p.setBorder(new EmptyBorder(20, 40, 20, 40));
		for(int i=0; i<6; i++) {
			JPanel pp = new JPanel(new BorderLayout());
			
			String path = System.getProperty("user.dir")+"\\�����ڷ�\\main\\"+bn[i]+".png";
			path = path.replace('\\', '/');
			btn[i] = new JButton(new ImageIcon(path));
			btn[i].setOpaque(false);
			btn[i].setBackground(Color.white);
			btn[i].setBorderPainted(false);
			btn[i].addActionListener(this);
			
			l[i] = new JLabel(ln[i], JLabel.CENTER);
			
			pp.add(btn[i], BorderLayout.CENTER);
			pp.add(l[i], BorderLayout.SOUTH);
			
			p.add(pp);
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
			new ledgerInsert();
		} else if(bt==btn[2]) {
			new List();
		} else if(bt==btn[3]) {
			new Chart();
		} else if(bt==btn[4]) {
			new MemberUpdate();
		} else if(bt==btn[5]) {
			System.exit(0);
		}
	}
}
