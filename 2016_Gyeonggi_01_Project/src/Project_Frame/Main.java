package Project_Frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {
	JPanel[] p = new JPanel[6];
	JButton[] img = new JButton[6];
	JLabel[] l = new JLabel[6];
	String[] ln = {"����� �޷º���","����� �Է�","����&���� ��ȸ","����&���� ��Ʈ","ȸ����������"," �����ϱ� "};
	
	public Main() {
		setTitle("����");
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JPanel pp = new JPanel(new GridLayout(2, 3, 50, 30));
		pp.setBorder(new EmptyBorder(40, 30, 20, 30));
		
		 
		img[1] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("mainIcon/�Է�.png")));
		img[2] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("mainIcon/����.png")));
		img[3] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("mainIcon/��Ʈ.png")));
		img[4] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("mainIcon/ȸ������.png")));
		img[5] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("mainIcon/����.png")));
		
		for(int i=0; i<6; i++) {
			p[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
			l[i] = new JLabel(ln[i]);
			img[i].setBorderPainted(false);
			img[i].setBackground(Color.white);
			img[i].setOpaque(false);
			
			p[i].add(img[i]);
			p[i].add(l[i]);
			pp.add(p[i]);
			img[i].addActionListener(this);
		}
		
		add(pp);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==img[0]) {
			this.dispose();
			new CalendarSelect();
		} else if(btn==img[1]) {
			this.dispose();
			new ledgerInsert();
		} else if(btn==img[2]) {
			this.dispose();
			new ledgerSelect();
		} else if(btn==img[3]) {
			this.dispose();
			new ledgerChart();
		} else if(btn==img[4]) {
			this.dispose();
			new MemberSelect();
		} else if(btn==img[5]) {
			System.exit(0);;
		}
	}
}
