package Project_Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JFrame implements ActionListener {
	JMenuBar mb = new JMenuBar();
	JMenu menu1 = new JMenu("扁夯沥焊包府");
	JMenu menu2 = new JMenu("荐碍沥焊包府");
	JMenu menu3 = new JMenu("己利包府");
	JMenuItem item1 = new JMenuItem("切积包府");
	JMenuItem item2 = new JMenuItem("苞格包府");
	JMenuItem item3 = new JMenuItem("荐碍包府");
	JMenuItem item4 = new JMenuItem("切苞喊 己利");
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
		
		if(cmd.equals("切积包府")) {
			new StudentSelect();
			this.frame.dispose();
		} else if(cmd.equals("苞格包府")) {
			
		} else if(cmd.equals("荐碍包府")) {
			
		} else if(cmd.equals("切苞喊 己利")) {
			
		}
	}
}
