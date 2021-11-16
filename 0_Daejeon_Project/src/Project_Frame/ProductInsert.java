package Project_Frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

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
	JTextField t2 = new JTextField();
	JTextField t3 = new JTextField(3);
	JRadioButton rb1 = new JRadioButton("����");
	JRadioButton rb2 = new JRadioButton("����");
	JCheckBox[] cb = new JCheckBox[7];
	String[] cn = {"S","M","L","XL","44","46","48"};
	JButton btn1 = new JButton("�̹��� �ҷ�����");
	JButton btn2 = new JButton("��ǰ�߰�");
	JPanel cbp = new JPanel();
	String path1 = null;
	
	public ProductInsert() {
		setTitle("��ǰ�߰�");
		setSize(800, 500);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(1, 2));
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(new EmptyBorder(10, 10, 10, 10));
		p1.add(img, BorderLayout.CENTER);
		p1.add(new JLabel("<��ǰ �̹���>"), BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel p3 = new JPanel(new GridLayout(5, 1));
		p3.setBorder(new EmptyBorder(0, 0, 0, 5));
		p3.add(new JLabel("��ǰ��"));
		p3.add(new JLabel("Ÿ��"));
		p3.add(new JLabel("����"));
		p3.add(new JLabel("������"));
		p3.add(new JLabel("����"));
		
		JPanel p4 = new JPanel(new GridLayout(5, 1));
		JPanel rbp = new JPanel();
		rbp.add(rb1); rbp.add(rb2);
		JPanel pp = new JPanel();
		pp.add(t3); pp.add(new JLabel("��"));
		p4.add(t1);
		p4.add(rbp);
		p4.add(t2);
		p4.add(cbp);
		p4.add(pp);
		
		JPanel p5 = new JPanel(new GridLayout(1, 2, 5, 5));
		p5.add(btn1); p5.add(btn2);
		
		p2.add(p3, BorderLayout.WEST);
		p2.add(p4, BorderLayout.CENTER);
		p2.add(p5, BorderLayout.SOUTH);
		
		p.add(p1);
		p.add(p2);
		
		add(p);
		
		ButtonGroup g = new ButtonGroup();
		g.add(rb1); g.add(rb2);
		rb1.setActionCommand(rb1.getText());
		rb2.setActionCommand(rb2.getText());
		
		for(int i=0; i<7; i++) {
			cb[i] = new JCheckBox(cn[i]);
		}
		
		rb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				typeSetting();
			}
		});
		
		rb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				typeSetting();
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser("C:\\Users\\Public\\Pictures\\Sample Pictures");
				fc.setFileFilter(new FileNameExtensionFilter("Image files (*.jpg)", "jpg"));
				fc.setFileFilter(new FileNameExtensionFilter("Image files (*.png)", "png"));
				
				int res = fc.showOpenDialog(null);
				
				if(res!=JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				path1 = fc.getSelectedFile().getPath();
				img.setIcon(new ImageIcon(path1));
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��� ���� �Է����ּ���.");
					return;
				}
				
				if(rb1.isSelected()) {
					if(cb[0].isSelected()==false && cb[1].isSelected()==false && cb[2].isSelected()==false && cb[3].isSelected()==false) {
						JOptionPane.showMessageDialog(null, "��� ���� �Է����ּ���.");
						return;
					}
				} else {
					if(cb[4].isSelected()==false && cb[5].isSelected()==false && cb[6].isSelected()==false) {
						JOptionPane.showMessageDialog(null, "��� ���� �Է����ּ���.");
						return;
					}
				}
				
				String sz=null;
				
				if(rb1.isSelected()) {
					if(cb[0].isSelected()) {
						sz = cb[0].getText();
					}
					if(cb[1].isSelected()) {
						if(sz==null) {
							sz = cb[1].getText();
						} else {
							sz = sz + "," + cb[1].getText();
						}
					}
					if(cb[2].isSelected()) {
						if(sz==null) {
							sz = cb[2].getText();
						} else {
							sz = sz + "," + cb[2].getText();
						}
					}
					if(cb[3].isSelected()) {
						if(sz==null) {
							sz = cb[3].getText();
						} else {
							sz = sz + "," + cb[3].getText();
						}
					}
				} else {
					if(cb[4].isSelected()) {
						sz = cb[4].getText();
					}
					if(cb[5].isSelected()) {
						if(sz==null) {
							sz = cb[5].getText();
						} else {
							sz = sz + "," + cb[5].getText();
						}
					}
					if(cb[6].isSelected()) {
						if(sz==null) {
							sz = cb[6].getText();
						} else {
							sz = sz + "," + cb[6].getText();
						}
					}
				}
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `type`, `price`, `size`, `amount`) "
							+ "VALUES ('"+t1.getText()+"', '"+g.getSelection().getActionCommand()+"', '"+t2.getText()+"', '"+sz+"', '"+t3.getText()+"');");
					
					
					try {
						String path2 = System.getProperty("user.dir")+"\\�����ڷ�\\images\\"+t1.getText()+".PNG";
						path2 = path2.replace('\\', '/');
						
						FileInputStream in = new FileInputStream(path1);
						FileOutputStream out = new FileOutputStream(path2);
						
						int data=0;
						
						while((data=in.read())!=-1) {
							out.write(data);
						}
						in.close();
						out.close();
						
						dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		typeSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void typeSetting() {
		cbp.removeAll();
		cbp.updateUI();
		if(rb1.isSelected()) {
			cbp.add(cb[0]);
			cbp.add(cb[1]);
			cbp.add(cb[2]);
			cbp.add(cb[3]);
		} else {
			cbp.add(cb[4]);
			cbp.add(cb[5]);
			cbp.add(cb[6]);
		}
	}
}