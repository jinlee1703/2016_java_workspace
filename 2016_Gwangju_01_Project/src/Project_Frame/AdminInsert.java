package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class AdminInsert extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[9];
	String[] ln = {"성명","주민등록번호","주소","출신학교","연락처","학년","반","번호","담당교수"};
	JTextField[] text = new JTextField[6];
	JComboBox[] combo = new JComboBox[3];
	JButton btn1 = new JButton("입력");
	JButton btn2 = new JButton("닫기");
	
	public AdminInsert() {
		setTitle("학생 입력");
		setSize(500, 250);
		setLocationRelativeTo(null);
		
		for(int i=0; i<9; i++) {
			label[i] = new JLabel(ln[i]);
		}
		
		for(int i=0; i<6; i++) {
			text[i] = new JTextField();
		}
		
		for(int i=0; i<3; i++) {
			combo[i] = new JComboBox();
		}
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(5, 1, 3, 3));
		p1.setBorder(new EmptyBorder(15, 0, 0, 0));
		p1.setPreferredSize(new Dimension(200, 140));
		for(int i=0; i<5; i++) {
			p1.add(label[i]);
			p1.add(text[i]);
		}
		
		JPanel p2 =new JPanel(new GridLayout(4, 1, 3, 3));
		p2.setBorder(new EmptyBorder(0, 0, 0, 0));
		p2.setPreferredSize(new Dimension(200, 110));
		p2.add(label[5]); p2.add(combo[0]);
		p2.add(label[6]); p2.add(combo[1]);
		p2.add(label[7]); p2.add(text[5]);
		p2.add(label[8]);  p2.add(combo[2]);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
		p3.setBorder(new EmptyBorder(-10, -20, 0, -20));
		p3.add(p1);
		p3.add(p2);
		
		JPanel p4 = new JPanel(new FlowLayout());
		p4.add(btn1); p4.add(btn2);
		
		p.add(p3, BorderLayout.CENTER);
		p.add(p4, BorderLayout.SOUTH);
		
		add(p);
		
		ComboInit();
		
		text[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from admin where Jumin='"+text[0].getText()+"'");
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "이미 등록된 학생입니다", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "중복확인완료", "중복 확인", JOptionPane.INFORMATION_MESSAGE);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(text[0].getText().equals("") || text[1].getText().equals("") || text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("") || text[5].getText().equals("") || combo[0].getSelectedItem().toString().equals("") || combo[1].getSelectedItem().toString().equals("") || combo[2].getSelectedItem().toString().equals("")) {
				JOptionPane.showMessageDialog(null, "입력되지 않은 항목이 있습니다!", "입력 오류", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					int a = Integer.parseInt(text[5].getText());
					String b = String.format("%02d", a);
					
					DBInterface.Stmt.execute("INSERT INTO `world000`.`admin` (`Name`, `Jumin`, `Address`, `School`, `Phone`) "
							+ "VALUES ('"+text[0].getText()+"', '"+text[1].getText()+"', '"+text[2].getText()+"', '"+text[3].getText()+"', '"+text[4].getText()+"');");
					
					DBInterface.Stmt.execute("INSERT INTO `world000`.`appraisal` (`Number`, `Onesemester`, `twosemester`) "
							+ "VALUES ('"+combo[0].getSelectedItem().toString()+combo[1].getSelectedItem().toString()+b+"', '0', '0');");
					
					JOptionPane.showMessageDialog(null, "입력이 완료되었습니다.", "입력 완료", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			this.dispose();
			new AdminSelect();
		}
	}
	
	public void ComboInit() {
		for(int i=1; i<4; i++) {
			combo[0].addItem(i);
		}
		
		for(int i=1; i<7; i++) {
			combo[1].addItem(i);
		}
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from protector");
			
			while(rs.next()) {
				combo[2].addItem(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		combo[0].setSelectedIndex(-1);
		combo[1].setSelectedIndex(-1);
		combo[2].setSelectedIndex(-1);
	}
}
