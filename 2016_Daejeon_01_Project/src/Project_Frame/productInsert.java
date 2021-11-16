package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.mysql.jdbc.Buffer;

import Project_DBInterface.DBInterface;

public class productInsert extends JFrame implements ActionListener {
	JLabel img = new JLabel();
	JLabel label = new JLabel("<상품 이미지>");
	JLabel[] l = new JLabel[5];
	String[] ln = {"상품명","타입","가격","사이즈","수량"};
	JPanel[] pp = new JPanel[5];
	JTextField text1 = new JTextField();
	JRadioButton rb1 = new JRadioButton("상의");
	JRadioButton rb2 = new JRadioButton("하의");
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JCheckBox check1 = new JCheckBox("S");
	JCheckBox check2 = new JCheckBox("M");
	JCheckBox check3 = new JCheckBox("L");
	JCheckBox check4 = new JCheckBox("XL");
	JLabel g = new JLabel("개");
	JButton btn1 = new JButton("이미지 불러오기");
	JButton btn2 = new JButton("상품추가");	
	String filePath;
	
	public productInsert() {
		setTitle("상품추가");
		setSize(750, 450);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setPreferredSize(new Dimension(400, 450));
		label.setHorizontalAlignment(label.CENTER);
		img.setBorder(new EmptyBorder(5, 5, 5, 5));
		p1.add(img, BorderLayout.CENTER);
		p1.add(label, BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setPreferredSize(new Dimension(350, 450));
		
		JPanel p21 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 20));
		for(int i=0; i<5; i++) {
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(40, 50));
			pp[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
			pp[i].setPreferredSize(new Dimension(300, 50));
		}
		text1.setPreferredSize(new Dimension(300, 50));
		text2.setPreferredSize(new Dimension(300, 50));
		text3.setPreferredSize(new Dimension(40, 20));
		ButtonGroup bg = new ButtonGroup(); bg.add(rb1); bg.add(rb2);
		pp[0].add(text1);
		pp[1].add(rb1); pp[1].add(rb2); 
		pp[2].add(text2);
		pp[3].add(check1); pp[3].add(check2); pp[3].add(check3); pp[3].add(check4);
		pp[4].add(text3); pp[4].add(g);
		for(int i=0; i<5; i++) {
			p21.add(l[i]);
			p21.add(pp[i]);
		}
		
		JPanel p22 = new JPanel(new GridLayout(1, 2, 5, 5));
		p2.setBorder(new EmptyBorder(10, 10, 10, 10));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		p22.add(btn1); p22.add(btn2);
		
		p2.add(p21, BorderLayout.CENTER);
		p2.add(p22, BorderLayout.SOUTH);
		
		p.add(p1);
		p.add(p2);
		
		add(p);
		
		Init();
		
		rb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Productsize();
			}
		});
		
		rb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Productsize();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Image files (*.jpg)", "jpg");
			FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Image files (*.png)", "png");
			fc.addChoosableFileFilter(filter1);
			fc.addChoosableFileFilter(filter2);
			
			int ret = fc.showOpenDialog(null);
			
			if(ret != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			filePath = fc.getSelectedFile().getPath();
			img.setIcon(new ImageIcon(filePath));
			pack();
		} else {
			if(text1.getText().equals("") || text2.getText().equals("") || (check1.isSelected()==false && check2.isSelected()==false && check3.isSelected()==false && check4.isSelected()==false) || text3.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					String t;
					if(rb1.isSelected()==true) {
						t = rb1.getText();
					} else {
						t = rb2.getText();
					}
					
					String sz = null;
					
					if(check1.isSelected()==true) {
						if(sz==null) {
							sz = check1.getText();
						} else {
							
						}
					}
					
					if(check2.isSelected()==true) {
						if(sz==null) {
							sz = check2.getText();
						} else {
							sz = sz + "," + check2.getText();
						}
					}

					if(check3.isSelected()==true) {
						if(sz==null) {
							sz = check3.getText();
						} else {
							sz = sz + "," + check3.getText();
						}
					}
			
					if(check4.isSelected()==true) {
						if(sz==null) {
							sz = check4.getText();
						} else {
							sz = sz + "," + check4.getText();
						}
					}
					
					DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `type`, `price`, `size`, `amount`) "
							+ "VALUES ('"+text1.getText()+"', '"+t+"', '"+text2.getText()+"', '"+sz+"', '"+text3.getText()+"');");
					
					try {
						String path = getClass().getClassLoader().getResource("images/").toString();
						path = path.substring(6, path.length()) + text1.getText()+".PNG";
						System.out.println(path);
						
						FileInputStream is = new FileInputStream(filePath);
						FileOutputStream os = new FileOutputStream(path);
						
						int data = 0;
						while((data=is.read())!=-1) {
							os.write(data);
						}
						is.close();
						os.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void Init() {
		rb1.setSelected(true);
		Productsize();
	}
	
	public void Productsize() {
		if(rb1.isSelected()==true) {
			check4.setVisible(true);
			check1.setText("S");
			check2.setText("M");
			check3.setText("L");
			check4.setText("XL");
		} else {
			check4.setVisible(false);
			check1.setText("44");
			check2.setText("46");
			check3.setText("48");
		}
	}
}
