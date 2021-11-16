package Project_Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Project_DBInterface.DBInterface;

public class SalesSelect extends JFrame implements ActionListener {
	Date d = new Date();
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String[] ln = {"헤어 주문",s.format(d),"영업번호","주문일자","방문시간:","고객번호","고객명","헤어번호","헤어명","단가:","이벤트","금액"};
	JLabel[] label = new JLabel[ln.length];
	JTextField[] text = new JTextField[7];
	JComboBox[] combo = new JComboBox[3];
	JButton btn1 = new JButton("등록");
	JButton btn2 = new JButton("취소");
	
	public SalesSelect() {
		setTitle("주문");
		setSize(600, 430);
		setResizable(false);
		setLocationRelativeTo(null);
		
		Init();
		
		JPanel basePanel = new JPanel(new FlowLayout());
		basePanel.setBorder(new EmptyBorder(-5, 0, 0, 0));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
		p1.setPreferredSize(new Dimension(getWidth(), 50));
		p1.setBackground(Color.gray);
		p1.add(label[0]); p1.add(label[1]);
		label[0].setFont(new Font("바탕", Font.BOLD, 25));
		label[0].setBorder(new EmptyBorder(0, 100, 0, 0));
		label[1].setBorder(new EmptyBorder(0, 200, 0, 0));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.setPreferredSize(new Dimension(getWidth(), 50));
		p2.add(label[2]); p2.add(text[0]);
		
		JPanel p31 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 5));
		p31.setPreferredSize(new Dimension(getWidth(), 60));
		p31.add(label[3]); p31.add(text[1]); p31.add(label[4]); p31.add(text[2]);
		p31.add(label[5]); p31.add(text[3]); p31.add(label[6]); p31.add(text[4]);
		
		JPanel p32 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 5));
		p32.setPreferredSize(new Dimension(getWidth(), 100));
		p32.add(label[7]); p32.add(combo[0]); p32.add(label[8]); p32.add(combo[1]);
		p32.add(label[9]); p32.add(text[5]);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.setPreferredSize(new Dimension(getWidth(), 175));
		p3.setBorder(new LineBorder(Color.black));
		p3.add(p31); p3.add(p32);
		
		JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 5));
		p4.setPreferredSize(new Dimension(getWidth(), 50));
		p4.add(label[10]); p4.add(combo[2]); p4.add(label[11]); p4.add(text[6]);
		
		JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 200, 0));
		p5.add(btn1); p5.add(btn2);
		
		basePanel.add(p1);
		basePanel.add(p2);
		basePanel.add(p3);
		basePanel.add(p4);
		basePanel.add(p5);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void Init() {
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);

			if(i>1) {
				label[i].setPreferredSize(new Dimension(60, 20));
			}
		}
		
		for(int i=0; i<7; i++) {
			text[i] = new JTextField(10);
		}
		
		for(int i=0; i<3; i++) {
			combo[i] = new JComboBox();
			combo[i].setPreferredSize(new Dimension(112, 25));
		}
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from sales order by id desc"); rs.next();
			text[0].setText(Integer.toString(rs.getInt(1)+1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat s2 = new SimpleDateFormat("hh:mm:ss");
		text[1].setText(s1.format(d));
		text[2].setText(s1.format(d));
		text[3].setText((String) ClientSelect.table.getValueAt(ClientSelect.table.getSelectedRow(), 0));
		text[4].setText((String) ClientSelect.table.getValueAt(ClientSelect.table.getSelectedRow(), 1));
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from price");
			
			while(rs.next()) {
				combo[0].addItem(rs.getString(1));
				combo[1].addItem(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from discount order by discount_percent");
			
			while(rs.next()) {
				combo[2].addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		text[0].setEditable(false);
		text[1].setEnabled(false);
		text[2].setEnabled(false);
		text[3].setEnabled(false);
		text[4].setEnabled(false);
		
		text[0].setHorizontalAlignment(text[0].CENTER);
		text[1].setHorizontalAlignment(text[1].RIGHT);
		text[2].setHorizontalAlignment(text[2].RIGHT);
		text[3].setHorizontalAlignment(text[3].CENTER);
		text[4].setHorizontalAlignment(text[4].CENTER);
		text[5].setHorizontalAlignment(text[5].RIGHT);
		text[6].setHorizontalAlignment(text[0].CENTER);
	}
}

