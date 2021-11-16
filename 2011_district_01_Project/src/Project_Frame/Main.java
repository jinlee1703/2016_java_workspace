package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class Main extends JFrame implements ActionListener {
	JLabel label = new JLabel("마르노 미용실 영업관리");
	JButton[] btn = new JButton[4];
	String[] bn = {"고객관리","주문관리","영업현황","종료"};
	
	
	public Main() {
		setTitle("Main");
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		basePanel p = new basePanel();
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setPreferredSize(new Dimension(500, 50));
		p1.setBackground(Color.BLACK);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setForeground(Color.white);
		p1.add(label);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 20));
		p2.setPreferredSize(new Dimension(250, 250));
		p2.setBackground(Color.CYAN);
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].setPreferredSize(new Dimension(150, 40));
			btn[i].addActionListener(this);
			btn[i].setFont(new Font("맑은 고딕", Font.BOLD, 18));
			p2.add(btn[i]);
		}
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		
		
		add(p, BorderLayout.CENTER);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	class basePanel extends JPanel {
		Image img = new ImageIcon(getClass().getClassLoader().getResource("Data/img.jpg")).getImage();
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new ClientSelect();
		} else if(bt==btn[1]) {
			
		} else if(bt==btn[2]) {
			
		} else {
			System.exit(0);
		}
	}
}


