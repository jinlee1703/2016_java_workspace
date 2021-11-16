package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Project_DBInterface.DBInterface;

public class CalendarSelect extends JFrame {
	JButton yearMinus = new JButton("◀");
	JButton yearPlus = new JButton("▶");
	static JLabel yearl = new JLabel("2016");
	JLabel l1 = new JLabel("년    ");
	JLabel l2 = new JLabel("월");
	static JComboBox combo = new JComboBox();
	JPanel[] dp = new JPanel[42];
	static JLabel[] dn = new JLabel[42];
	static JLabel[] bl = new JLabel[42];
	static JLabel[] rl = new JLabel[42];
	JPanel cp = new JPanel(new GridLayout(6, 7));
	JPanel hp = new JPanel(new GridLayout(1, 7));
	JLabel[] hl = new JLabel[7];
	String[] header = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
	static Calendar cal = Calendar.getInstance();
	int year; int month; int day;
	
	public CalendarSelect() {
		setTitle("달력");
		setSize(695, 500);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		yearl.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		combo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		p1.add(yearMinus); p1.add(yearl); p1.add(yearPlus); p1.add(l1); p1.add(combo); p1.add(l2);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(hp, BorderLayout.NORTH);
		p2.add(cp, BorderLayout.CENTER);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		
		add(p, BorderLayout.CENTER);
		
		Init();
		try {
			CalendarInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		yearMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				yearl.setText(Integer.toString(Integer.parseInt(yearl.getText())-1));
				try {
					CalendarInit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		yearPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				yearl.setText(Integer.toString(Integer.parseInt(yearl.getText())+1));
				try {
					CalendarInit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					CalendarInit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init() {
		for(int i=0; i<7; i++) {
			hl[i] = new JLabel(header[i]);
			hl[i].setHorizontalAlignment(hl[i].CENTER);
			hp.add(hl[i]);
		}
		
		hl[0].setForeground(Color.red);
		hl[6].setForeground(Color.blue);
		
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		
		yearl.setText(Integer.toString(year));
		
		for(int i=1; i<13; i++) {
			combo.addItem(i);
		}
		
		combo.setSelectedItem(month);
		
		for(int i=0; i<42; i++) {
			dp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
			
			dn[i] = new JLabel();
			dn[i].setPreferredSize(new Dimension(80, 15));
			dn[i].setFont(new Font("맑은 고딕", Font.BOLD, 11));
			
			bl[i] = new JLabel();
			bl[i].setPreferredSize(new Dimension(80, 15));
			bl[i].setFont(new Font("맑은 고딕", Font.BOLD, 11));
			
			rl[i] = new JLabel();
			rl[i].setPreferredSize(new Dimension(80, 15));
			rl[i].setFont(new Font("맑은 고딕", Font.BOLD, 11));
			
			dp[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
			bl[i].setForeground(Color.blue);
			rl[i].setForeground(Color.red);
			
			dp[i].add(dn[i]);
			dp[i].add(bl[i]);
			dp[i].add(rl[i]);
			
			cp.add(dp[i]);
		}
		
		cp.setBorder(new EmptyBorder(0, 0, 10, 0));
	}
	
	public static void CalendarInit() throws Exception {
		cal.set(Calendar.YEAR, Integer.parseInt(yearl.getText()));
		cal.set(Calendar.MONTH, Integer.parseInt(combo.getSelectedItem().toString())-1);
		cal.set(Calendar.DATE, 1);
		
		int startDay = cal.get(Calendar.DAY_OF_WEEK);
		int lastDay = cal.getActualMaximum(cal.DATE);
		
		int cnt = 1;
		String d;
		
		for(int i=0; i<42; i++) {
			dn[i].setText("");
			bl[i].setText("");
			rl[i].setText("");
			bl[i].setToolTipText("");
		}
		
		String nd;
		
		for(int i=startDay-1; i<lastDay+startDay-1; i++) {
			d = String.format("%02d", cnt);
			dn[i].setText(d);
			
			nd = yearl.getText()+"-"+String.format("%02d", Integer.parseInt(combo.getSelectedItem().toString()))+"-"+dn[i].getText();
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and date='"+nd+"'");
			
			while(rs.next()) {
				if(rs.getString(3).equals("수입")) {
					bl[i].setText("수입: "+rs.getString(6)+"원");
					bl[i].setToolTipText(rs.getString(7));
				} else if(rs.getString(3).equals("지출")) {
					rl[i].setText("지출: "+rs.getString(6)+"원");
				}
			}
			cnt+=1;
		}
	}
}
