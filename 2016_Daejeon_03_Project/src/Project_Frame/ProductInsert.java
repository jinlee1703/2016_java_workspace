package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	JTextField t1 = new JTextField();
	JRadioButton rb1 = new JRadioButton("상의");
	JRadioButton rb2 = new JRadioButton("하의");
	JTextField t2 = new JTextField();
	JCheckBox[] cb = new JCheckBox[7];
	String[] cn = {"S","M","L","XL","44","46","48"};
	JTextField t3 = new JTextField(3);
	JButton btn1 = new JButton("이미지 불러오기");
	JButton btn2 = new JButton("상품추가");
	JPanel p222;
	String filepath;
	
	public ProductInsert() {
		setTitle("상품추가");
		setSize(700, 450);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setPreferredSize(new Dimension(300, 600));
		p1.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		p1.add(img, BorderLayout.CENTER);
		p1.add(new JLabel("<상품 이미지>", JLabel.CENTER), BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 10, 10, 10));
		p2.setPreferredSize(new Dimension(350, 600));
		
		JPanel p21 = new JPanel(new GridLayout(5, 1));
		p21.setBorder(new EmptyBorder(0, 0, 0, 5));
		p21.add(new JLabel("상품명"));
		p21.add(new JLabel("타입"));
		p21.add(new JLabel("가격"));
		p21.add(new JLabel("사이즈"));
		p21.add(new JLabel("수량"));
		
		JPanel p22 = new JPanel(new GridLayout(5, 1));
		p22.add(t1);
		JPanel p221 = new JPanel();
		ButtonGroup g = new ButtonGroup();
		g.add(rb1); g.add(rb2);
		rb1.setActionCommand(rb1.getText());
		rb2.setActionCommand(rb2.getText());
		p221.add(rb1); p221.add(rb2);
		p22.add(p221);
		p22.add(t2);
		p222 = new JPanel();
		p22.add(p222);
		JPanel p223 = new JPanel();
		p223.add(t3); p223.add(new JLabel("개"));
		p22.add(p223);
		
		for(int i=0; i<7; i++) {
			cb[i] = new JCheckBox(cn[i]);
			p222.add(cb[i]);
		}
		
		JPanel p23 = new JPanel(new GridLayout(1, 2, 5, 5));
		p23.add(btn1); p23.add(btn2);
		
		p2.add(p21, BorderLayout.WEST);
		p2.add(p22, BorderLayout.CENTER);
		p2.add(p23, BorderLayout.SOUTH);
		
		p.add(p1);
		p.add(p2);
		
		add(p);
		
		rb1.setSelected(true);
		cbSetting();
		
		rb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cbSetting();
			}
		});
		
		rb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cbSetting();
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser("C:\\Users\\Public\\Pictures\\Sample Pictures");	//그림 열기
				FileNameExtensionFilter f1 = new FileNameExtensionFilter("Image files (*.jpg)", "jpg");
				FileNameExtensionFilter f2 = new FileNameExtensionFilter("Image files (*.png)", "png");
				fc.setFileFilter(f1); fc.setFileFilter(f2);
				
				int res = fc.showOpenDialog(null);
				
				if(res!=JFileChooser.APPROVE_OPTION) {
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
					if(! cb[0].isSelected() && ! cb[1].isSelected() && ! cb[2].isSelected() && ! cb[3].isSelected()) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
						return;
					}
				} else {
					if(! cb[4].isSelected() && ! cb[5].isSelected() && ! cb[6].isSelected()) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
						return;
					}
				}
				
				String sz = null;
				
				if(rb1.isSelected()) {
					for(int i=0; i<4; i++) {
						if(cb[i].isSelected()) {
							sz=sz+",";
						}
					}
				} else {
					for(int i=4; i<7; i++) {
						if(cb[i].isSelected()) {
							sz=sz+",";
						}
					}
				}
				
				sz=sz.substring(0, sz.length()-1);
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `type`, `price`, `size`, `amount`) "
							+ "VALUES ('"+t1.getText()+"', '"+g.getSelection().getActionCommand()+"', '"+t2.getText()+"', '"+sz+"', '"+t3.getText()+"');");
					
					String path = System.getProperty("user.dir")+"\\res\\images\\"+t1.getText()+".jpg";
				
					try {
						FileInputStream is = new FileInputStream(filepath);
						FileOutputStream os = new FileOutputStream(path);
						
						int data=0;
						
						while((data=is.read())!=-1) {
							os.write(data);
						}
						is.close();
						os.close();
						
						dispose();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void cbSetting() {
		p222.removeAll();
		p222.updateUI();
		if(rb1.isSelected()) {
			p222.add(cb[0]);
			p222.add(cb[1]);
			p222.add(cb[2]);
			p222.add(cb[3]);
		} else {
			p222.add(cb[4]);
			p222.add(cb[5]);
			p222.add(cb[6]);
		}
	}
}
