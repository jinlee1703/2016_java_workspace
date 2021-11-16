package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main1 extends JFrame implements ActionListener {
	JLabel img = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("DataFiles/Image2.jpg")));
	JButton btn1 = new JButton("회원정보조회/변경");
	JButton btn2 = new JButton("상품주문");
	JButton btn3 = new JButton("주문이력조회");
	
	public Main1() {
		setTitle("StoreClient for Customer");
		setSize(530, 430);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p = new JPanel(new FlowLayout());
		btn1.setPreferredSize(new Dimension(165, 25));
		btn2.setPreferredSize(new Dimension(165, 25));
		btn3.setPreferredSize(new Dimension(165, 25));
		p.add(btn1); p.add(btn2); p.add(btn3);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		basePanel.add(img, BorderLayout.CENTER);
		basePanel.add(p, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			new ClientUpdate();
		} else if(btn==btn2) {
			new SalesInsert();
		} else if(btn==btn3) {
			new ClientSalesSelect();
		}
	}
}
