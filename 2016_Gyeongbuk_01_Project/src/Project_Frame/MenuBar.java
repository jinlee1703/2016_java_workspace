package Project_Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JFrame implements ActionListener {
	JMenuBar mb = new JMenuBar();
	JMenu menu1 = new JMenu("�⺻��������");
	JMenu menu2 = new JMenu("������������");
	JMenu menu3 = new JMenu("��������");
	JMenuItem item1 = new JMenuItem("�л�����");
	JMenuItem item2 = new JMenuItem("�������");
	JMenuItem item3 = new JMenuItem("��������");
	JMenuItem item4 = new JMenuItem("�а��� ����");
	JFrame frame;
	
	public MenuBar(JFrame frame) {
		this.frame = frame;
		frame.setJMenuBar(mb);
		
		menu1.add(item1);
		menu1.add(item2);
		mb.add(menu1);
		
		menu2.add(item3);
		mb.add(menu2);
		
		menu3.add(item4);
		mb.add(menu3);
		
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		
		if(cmd.equals("�л�����")) {
			new StudentSelect();
			this.frame.dispose();
		} else if(cmd.equals("�������")) {
			
		} else if(cmd.equals("��������")) {
			
		} else if(cmd.equals("�а��� ����")) {
			
		}
	}
}
