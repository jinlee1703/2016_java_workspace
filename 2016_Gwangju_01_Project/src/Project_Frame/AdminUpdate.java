package Project_Frame;

import java.awt.BorderLayout;
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

public class AdminUpdate extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[6];
	String[] ln = {"학번","성명","주민등록번호","주소","출신학교","연락처"};
	JComboBox combo = new JComboBox();
	JTextField[] text = new JTextField[5];
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("닫기");
	
	public AdminUpdate() {
		setTitle("학생수정");
		setSize(250, 250);
		setLocationRelativeTo(null);
		
		for(int i=0; i<6; i++) {
			label[i] = new JLabel(ln[i]);
		}
		
		for(int i=0; i<5; i++) {
			text[i] = new JTextField();
		}
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(6, 2, 2, 2));
		p1.setBorder(new EmptyBorder(2, 5, 5, 10));
		p1.add(label[0]); p1.add(combo); 
		for(int i=1; i<6; i++) {
			p1.add(label[i]);
			p1.add(text[i-1]);
		}
		
		JPanel p2 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		p2.add(btn1); p2.add(btn2);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		ComboInit();
		
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ComboChange();
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
			if(text[0].getText().equals("") || text[1].getText().equals("") || text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "입력되지 않은 항목이 있습니다!", "입력 오류", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from report where Number='"+combo.getSelectedItem().toString()+"'");
					rs.next(); String a = rs.getString(1);
					
					DBInterface.Stmt.execute("DELETE FROM `world000`.`admin` WHERE `Jumin`='"+rs.getShort(1)+"';");
					
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "입력 완료", JOptionPane.INFORMATION_MESSAGE);
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
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from appraisal");
			
			while(rs.next()) {
				combo.addItem(rs.getString(1));
			}
			
			combo.setSelectedIndex(-1);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ComboChange() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("SELECT report.Number,admin.Name,admin.Jumin,admin.Address,admin.School,admin.Phone FROM admin join report on report.Jumin=admin.Jumin where number='"+combo.getSelectedItem().toString()+"';");
			
			if(rs.next()) {
				for(int i=0; i<5; i++) {
					text[i].setText(rs.getString(i+1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
