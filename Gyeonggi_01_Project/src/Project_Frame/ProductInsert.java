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

public class ProductInsert extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[4];
	String[] ln = {"ID","상품명","제조업체","단가"};
	JTextField[] text = new JTextField[3];
	JComboBox combo = new JComboBox();
	JButton btn1 = new JButton("등록");
	JButton btn2 = new JButton("닫기");
	
	public ProductInsert() {
		setTitle("상품등록");
		setSize(300, 320);
		setResizable(false);
		setLocationRelativeTo(null);
		
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setHorizontalAlignment(label[i].RIGHT);
		}
		
		for(int i=0; i<3; i++) {
			text[i] = new JTextField();
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(50, -70, 70, 60));
		
		JPanel p1 = new JPanel(new GridLayout(4, 2, 15, 10));
		p1.add(label[0]); p1.add(text[0]);
		p1.add(label[1]); p1.add(text[1]);
		p1.add(label[2]); p1.add(combo);
		p1.add(label[3]); p1.add(text[2]);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
		p2.setBorder(new EmptyBorder(15, 0, 0, -20));
		btn1.setPreferredSize(new Dimension(90, 30));
		btn2.setPreferredSize(new Dimension(90, 30));
		p2.add(btn1); p2.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		add(basePanel);
		
		FrameInit();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from supplier where name='"+combo.getSelectedItem().toString()+"'");
				rs.next(); String n = rs.getString(1);
				
				rs = DBInterface.Stmt.executeQuery("select * from product where name='"+text[1].getText()+"' and supplierid='"+n+"'");
				
				if(rs.next()) {
					JOptionPane.showMessageDialog(null, "해당 제조업체에 이미 등록된 상품명입니다.");
				} else if(text[1].getText().equals("") || text[2].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
				} else {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`product` (`name`, `supplierid`, `amount`) "
							+ "VALUES ('"+text[1].getText()+"', '"+n+"', '"+text[2].getText()+"');");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
			}
		} else {
			this.dispose();
		}
	}
	
	public void  FrameInit() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select count(*) from product");
			rs.next();
			text[0].setText(String.valueOf(rs.getInt(1)+1));
			text[0].setEnabled(false);
			
			rs = DBInterface.Stmt.executeQuery("select * from supplier");
			
			while(rs.next()) {
				combo.addItem(rs.getString(2));
			}
			combo.setSelectedIndex(-1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
