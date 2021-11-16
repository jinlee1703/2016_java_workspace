package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MovieReservation extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[5];
	String[] ln = {"영화명 :","시간 :","자리 :","예매시간 :","금액 :"};
	static JTextField[] text = new JTextField[5];
	JButton[] btn = new JButton[4];
	String[] bn = {"예매하기","영화선택","시간선택","자리선택"};
	
	public MovieReservation() {
		setTitle("영화예매");
		setSize(390, 242);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(70, 20));
			text[i] = new JTextField();
			text[i].setEditable(false);
			text[i].setPreferredSize(new Dimension(200, 30));
		}
		
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			btn[i].setPreferredSize(new Dimension(100, 30));
		}
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 14));
		p1.setPreferredSize(new Dimension(70, 300));
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		p2.setPreferredSize(new Dimension(200, 300));
		for(int i=0; i<5; i++) {
			p1.add(label[i]);
			p2.add(text[i]);
		}
		p2.add(btn[0]);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		p3.setPreferredSize(new Dimension(105, 300));
		for(int i=1; i<4; i++) {
			p3.add(btn[i]);
		}
		
		basePanel.add(p1, BorderLayout.WEST);
		basePanel.add(p2, BorderLayout.CENTER);
		basePanel.add(p3, BorderLayout.EAST);
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[1]) {
			
		} else if(bt==btn[2]) {
			if(text[1].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "영화를 먼저 선택해주세요.");
			} else {
				
			}
		} else if(bt==btn[3]) {
			if(text[2].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "시간을 먼저 선택해주세요.");
			} else {
				
			}
		} else if(bt==btn[0]) {
			if(text[0].getText().equals("") || text[1].getText().equals("") || text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "선택하지 않은 버튼이 있습니다.");
			} else {
				
			}
		}
	}
}
