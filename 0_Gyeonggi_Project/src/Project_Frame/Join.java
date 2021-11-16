package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Project_DBInterface.DBInterface;

public class Join extends JFrame {
	JPanel[] pp = new JPanel[6];
	JLabel[] l = new JLabel[6];
	String[] ln = {"È¸¿ø ¾ÆÀÌµð","È¸¿ø ºñ¹Ð¹øÈ£","ÀÌ¸§","ÁÖ¼Ò","ÀÌ¸ÞÀÏ","ÀÚµ¿¹æÁö¹®ÀÚ"};
	JTextField[] t = new JTextField[6];
	int[] ts = {10,10,5,20,20,5};
	JLabel cl1 = new JLabel();
	String rd = String.format("%04d", new Random().nextInt(10000));
	JLabel cl2 = new JLabel("¹®ÀÚ: "+rd);
	JButton btn1 = new JButton("°¡ÀÔ");
	JButton btn2 = new JButton("Ãë¼Ò");
	
	public Join() {
		setTitle("È¸¿ø°¡ÀÔ");
		setSize(400, 350);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("È¸¿ø°¡ÀÔ Æû")).setFont(new Font("HY°ß°íµñ", Font.PLAIN, 25));
		
		JPanel p2 = new JPanel(new GridLayout(6, 1));
		for(int i=0; i<6; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			l[i] = new JLabel(ln[i]+" :");
			l[i].setPreferredSize(new Dimension(100, 20));
			t[i] = new JTextField(ts[i]);
			
			pp[i].add(l[i]);
			pp[i].add(t[i]);
			p2.add(pp[i]);
		}
		
		pp[1].add(cl1);
		pp[5].add(cl2);
		cl2.setForeground(Color.red);
		
		JPanel p3 = new JPanel();
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		t[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(t[1].getText().matches("[0-9|a-z|A-Z|¤¡-¤¾|¤¿-¤Ó|°¡-ÆR]*")) {
					cl1.setText("(Ãë¾àÇÔ)");
					cl1.setForeground(Color.red);
				} else {
					cl1.setText("(¾ÈÀüÇÔ)");
					cl1.setForeground(Color.blue);
				}
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0; i<6; i++) {
					if(t[i].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "¸ðµÎ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						return;
					}
				}
				
				if(! rd.equals(t[5].getText())) {
					JOptionPane.showMessageDialog(null, "ÀÚµ¿¹æÁö¹®ÀÚ¿Í ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù.");
					return;
				}
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `address`, `email`) "
							+ "VALUES ('"+t[0].getText()+"', '"+t[1].getText()+"', '"+t[2].getText()+"', '"+t[3].getText()+"', '"+t[4].getText()+"');");
					
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
