package Project_Frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {
	JLabel img = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Data/university.jpg")));
	
	public Main() {
		setTitle("학사관리");
		setSize(500, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		
		new MenuBar(this);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.add(img);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
