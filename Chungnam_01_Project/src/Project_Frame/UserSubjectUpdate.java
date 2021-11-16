package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UserSubjectUpdate extends JFrame {
	JCheckBox[] c = new JCheckBox[4];
	String[] cn = {"Word","Java","Excel","PowerPoint"};
	
	JButton btn = new JButton("강좌수준선택");
	
	public UserSubjectUpdate() {
		setTitle("수강과목 선택");
		setSize(220, 210);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(15, 40, 2, 30));
		
		JPanel p1 = new JPanel(new GridLayout(4, 1, 15, 15));
		for(int i=0; i<cn.length; i++) {
			c[i] = new JCheckBox(cn[i]);
			c[i].setHorizontalAlignment(c[i].LEFT);
			p1.add(c[i]);
		}
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 10, 0, 0));
		p2.add(btn, BorderLayout.CENTER);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(c[0].isSelected()==false && c[1].isSelected()==false && c[2].isSelected()==false && c[3].isSelected()==false) {
					JOptionPane.showMessageDialog(null, "수강하실 강좌명을 선택해주세요.");
				} else {
					int a, s, d, f;
					if(c[0].isSelected()==true) {
						if(c[0].isSelected()==true) {
							a=1;
						} else {
							a=0;
						}
						
						if(c[1].isSelected()==true) {
							s=1;
						} else {
							s=0;
						}
						
						if(c[2].isSelected()==true) {
							d=1;
						} else {
							d=0;
						}
						
						if(c[3].isSelected()==true) {
							f=1;
						} else {
							f=0;
						}
						
						new UserSubjectSelect(a, s, d, f);
					}
				}
			}
		});
	}
}
