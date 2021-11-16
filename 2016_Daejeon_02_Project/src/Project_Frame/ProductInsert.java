package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FilenameFilter;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Project_DBInterface.DBInterface;

public class ProductInsert extends JFrame {
	JLabel img = new JLabel();
	JLabel[] l = new JLabel[5];
	String[] ln = {"상품명","타입","가격","사이즈","수량"};
	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	JTextField t3 = new JTextField(3);
	JRadioButton rb1 = new JRadioButton("상의");
	JRadioButton rb2 = new JRadioButton("하의");
	JCheckBox[] c = new JCheckBox[7];
	String[] cn = {"S","M","L","XL","44","46","48"};
	JButton btn1 = new JButton("이미지 불러오기");
	JButton btn2 = new JButton("상품추가");
	JPanel p212;
	String filepath;
	
	public ProductInsert() {
		setTitle("상품추가");
		setSize(640, 400);
		setLocationRelativeTo(null);
		
		Setting();
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(new EmptyBorder(5, 5, 10, 5));
		p1.setPreferredSize(new Dimension(300, 400));
		p1.add(img, BorderLayout.CENTER);
		p1.add(new JLabel("<상품 이미지>", JLabel.CENTER), BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 10, 10, 10));
		p2.setPreferredSize(new Dimension(300, 400));
		
		JPanel p21 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		JPanel p211 = new JPanel();
		p211.add(rb1); p211.add(rb2);
		
		p212 = new JPanel();
		p212.add(c[0]);
		p212.add(c[1]);
		p212.add(c[2]);
		p212.add(c[3]);
		
		JPanel p213 = new JPanel();
		p213.add(t3); p213.add(new JLabel("개"));
		
		p21.add(l[0]).setPreferredSize(new Dimension(40, 20)); p21.add(t1).setPreferredSize(new Dimension(240, 50));
		p21.add(l[1]).setPreferredSize(new Dimension(40, 20)); p21.add(p211).setPreferredSize(new Dimension(240, 50));
		p21.add(l[2]).setPreferredSize(new Dimension(40, 20)); p21.add(t2).setPreferredSize(new Dimension(240, 50));
		p21.add(l[3]).setPreferredSize(new Dimension(40, 20)); p21.add(p212).setPreferredSize(new Dimension(240, 50));
		p21.add(l[4]).setPreferredSize(new Dimension(40, 20)); p21.add(p213).setPreferredSize(new Dimension(240, 50));
		
		JPanel p22 = new JPanel(new GridLayout(1, 2, 5, 5));
		p22.add(btn1); p22.add(btn2);
		p2.add(p21, BorderLayout.CENTER);
		p2.add(p22, BorderLayout.SOUTH);
		
		p.add(p1); p.add(p2);
		
		add(p);
		
		rb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ProductSize();
			}
		});
		
		rb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ProductSize();
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter f1 = new FileNameExtensionFilter("Image files (*.jpg)", "jpg");
				FileNameExtensionFilter f2 = new FileNameExtensionFilter("Image files (*.png)", "png");
				fc.setFileFilter(f1); fc.setFileFilter(f2);
				
				int res = fc.showOpenDialog(null);
				
				if(res != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				filepath = fc.getSelectedFile().getPath();
				img.setIcon(new ImageIcon(filepath));
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
					return;
				}
				
				if(rb1.isSelected()) {
					if(! c[0].isSelected() && ! c[1].isSelected() && ! c[2].isSelected() && ! c[3].isSelected()) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
						return;
					}
				} else {
					if(! c[4].isSelected() && ! c[5].isSelected() && ! c[6].isSelected()) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
						return;
					}
				}
				
				int a,b;
				String cc;
				
				if(rb1.isSelected()) {
					a=0;
					b=4;
					cc="상의";
				} else {
					a=4;
					b=7;
					cc="하의";
				}
				
				String sz=null;
				
				for(int i=a; i<b; i++) {
					if(c[i].isSelected()) {
						if(sz==null) {
							sz = c[i].getText();
						} else {
							sz = sz + "," + c[i].getText();
						}
						
					}
				}
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `type`, `price`, `size`, `amount`) "
							+ "VALUES ('"+t1.getText()+"', '"+cc+"', '"+t2.getText()+"', '"+sz+"', '"+t3.getText()+"');");
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Setting() {
		for(int i=0; i<5; i++) {
			l[i] = new JLabel(ln[i]);
		}
		
		for(int i=0; i<7; i++) {
			c[i] = new JCheckBox(cn[i]);
		}
		
		ButtonGroup g = new ButtonGroup();
		g.add(rb1);
		g.add(rb2);
		
		rb1.setSelected(true);
	}
	
	public void ProductSize() {
		p212.removeAll();
		p212.updateUI();
		if(rb1.isSelected()) {
			p212.add(c[0]);
			p212.add(c[1]);
			p212.add(c[2]);
			p212.add(c[3]);
			repaint();
		} else {
			repaint();
			p212.add(c[4]);
			p212.add(c[5]);
			p212.add(c[6]);
		}
	}
}
