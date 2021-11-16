package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {
	JLabel title = new JLabel("��ǰ �Ǹ� ���α׷�");
	JButton btn1 = new JButton("������ ����");
	JButton btn2 = new JButton("�� �α���");
	
	public Main() {
		setTitle("����");
		setSize(400, 180);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
		btn1.setPreferredSize(new Dimension(150, 40));
		btn2.setPreferredSize(new Dimension(150, 40));
		p2.add(btn1); p2.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			new ManagerMenu();
			this.dispose();
		} else {
			new Login();
			this.dispose();
		}
	}
}
