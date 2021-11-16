package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Project_DBInterface.DBInterface;

public class CalendarView extends JFrame implements ActionListener {
	Date d = new Date();
	SimpleDateFormat f1 = new SimpleDateFormat("yyyy");
	SimpleDateFormat f2 = new SimpleDateFormat("M");
	JLabel yl = new JLabel(f1.format(d));
	JButton lb = new JButton("¢¸");
	JButton rb = new JButton("¢º");
	JComboBox combo = new JComboBox();
	JLabel[] wl = new JLabel[7];
	String[] wn = {"ÀÏ","¿ù","È­","¼ö","¸ñ","±Ý","Åä"};
	JPanel[] dp = new JPanel[42];
	JLabel[] dl = new JLabel[42];
	JLabel[] pl = new JLabel[42];
	JLabel[] ml = new JLabel[42];
	
	public CalendarView() {
		setTitle("´Þ·Â");
		setSize(700, 550);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		JPanel p = new JPanel(new BorderLayout());
		JPanel pp = new JPanel();
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setBorder(new EmptyBorder(0, 0, 10, 0));
		yl.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		combo.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		for(int i=1; i<13; i++) combo.addItem(i);
		combo.setSelectedIndex(Integer.parseInt(f2.format(d))-1);
		p1.add(lb); p1.add(yl); p1.add(rb); p1.add(rb); p1.add(new JLabel("³â     ")); p1.add(combo); p1.add(new JLabel("¿ù"));
		
		JPanel p2 = new JPanel(new GridLayout(1, 7));
		for(int i=0; i<7; i++) {
			wl[i] = new JLabel(wn[i]+"¿äÀÏ");
			wl[i].setHorizontalAlignment(wl[i].CENTER);
			p2.add(wl[i]);
		}
		wl[0].setForeground(Color.red);
		wl[6].setForeground(Color.blue);
		
		JPanel p3 = new JPanel(new GridLayout(6, 7));
		for(int i=0; i<42; i++) {
			dp[i] = new JPanel();
			dp[i].setLayout(new BoxLayout(dp[i], BoxLayout.Y_AXIS));
			dp[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
			dl[i] = new JLabel(); pl[i] = new JLabel(); ml[i] = new JLabel();
			pl[i].setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 11)); ml[i].setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 11));
			pl[i].setForeground(Color.blue); ml[i].setForeground(Color.red);
			pl[i].setBorder(new EmptyBorder(5, 0, 0, 0)); ml[i].setBorder(new EmptyBorder(5, 0, 0, 0));
			
			dp[i].add(dl[i]);
			dp[i].add(pl[i]);
			dp[i].add(ml[i]);
			
			p3.add(dp[i]);
		}
		
		pp.add(p1); pp.add(p2);
		
		p.add(pp, BorderLayout.NORTH);
		p.add(p3, BorderLayout.CENTER);
		
		add(p);
		
		lb.addActionListener(this);
		rb.addActionListener(this);
		combo.addActionListener(this);
		
		CalendarRefresh();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void CalendarRefresh() {
		for(int i=0; i<42; i++) {
			dl[i].setText(" ");
			pl[i].setText(" ");
			ml[i].setText(" ");
		}
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, Integer.parseInt(yl.getText()));
		cal.set(Calendar.MONTH, Integer.parseInt(combo.getSelectedItem().toString())-1);
		cal.set(Calendar.DATE, 1);
		
		int fd = cal.get(Calendar.DAY_OF_WEEK);
		int ld = cal.getActualMaximum(cal.DATE);
		
		int cnt=1;
		String dd;
		
		for(int i=fd-1; i<fd+ld-1; i++) {
			dl[i].setText(String.format("%02d", cnt));
			
			dd = yl.getText()+"-"+String.format("%02d", Integer.parseInt(combo.getSelectedItem().toString()))+"-"+dl[i].getText();
			
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select sum(amount) from ledger where memberid='"+Login.id.getText()+"' and date='"+dd+"' and division='¼öÀÔ'");
				rs.next();
				if(rs.getString(1)!=null) {
					pl[i].setText("¼öÀÔ: "+rs.getInt(1)+"¿ø");
				}
				
				rs = DBInterface.Stmt.executeQuery("select sum(amount) from ledger where memberid='"+Login.id.getText()+"' and date='"+dd+"' and division='ÁöÃâ'");
				rs.next();
				if(rs.getString(1)!=null) {
					ml[i].setText("ÁöÃâ: "+rs.getInt(1)+"¿ø");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cnt+=1;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(lb)) {
			yl.setText(Integer.toString(Integer.parseInt(yl.getText())-1));
		} else if(e.getSource().equals(rb)) {
			yl.setText(Integer.toString(Integer.parseInt(yl.getText())+1));
		}
		
		CalendarRefresh();
	}
}
