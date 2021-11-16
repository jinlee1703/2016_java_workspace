package Project_Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Members extends JFrame {
	JTextField t1 = new JTextField(5);
	JTextField t2 = new JTextField(5);
	JButton btn = new JButton("검색");
	
	public Members() {
		setTitle("고객조회");
	}
}
