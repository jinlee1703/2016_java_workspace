package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.border.LineBorder;

import Project_DBInterface.DBInterface;

public class CalendarView extends JFrame implements ActionListener {
	SimpleDateFormat f1 = new SimpleDateFormat("yyyy");
	SimpleDateFormat f2 = new SimpleDateFormat("M");
	SimpleDateFormat f3 = new SimpleDateFormat("dd");
	JButton btn1 = new JButton("◀");
	JLabel yl = new JLabel(f1.format(new Date()));
	JButton btn2 = new JButton("▶");
	JComboBox combo = new JComboBox();
	String[] wn = {"일","월","화","수","목","금","토"};
	JLabel[] wl = new JLabel[7];
	JPanel[] pp = new JPanel[42];
	JLabel[] dl = new JLabel[42];
	JLabel[] db = new JLabel[42];
	JLabel[] dr = new JLabel[42];
	
	public CalendarView() {
		setTitle("달력");
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		for(int i=1; i<13; i++) {
			combo.addItem(i);
		}
		
		JPanel p = new JPanel(new BorderLayout());
		JPanel pt = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(btn1); p1.add(yl); p1.add(btn2); p1.add(new JLabel("년     ")); p1.add(combo); p1.add(new JLabel("월"));
		yl.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		combo.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		
		JPanel p2 = new JPanel(new GridLayout(1, 7));
		for(int i=0; i<wn.length; i++) {
			wl[i] = new JLabel(wn[i]+"요일");
			wl[i].setHorizontalAlignment(JLabel.CENTER);
			p2.add(wl[i]);
		}
		wl[0].setForeground(Color.red);
		wl[6].setForeground(Color.blue);
		
		JPanel p3 = new JPanel(new GridLayout(6, 7));
		for(int i=0; i<42; i++) {
			pp[i] = new JPanel();
			pp[i].setLayout(new BoxLayout(pp[i], BoxLayout.Y_AXIS));
			pp[i].setBorder(new LineBorder(Color.lightGray));
			
			dl[i] = new JLabel();
			db[i] = new JLabel();
			db[i].setForeground(Color.blue);
			dr[i] = new JLabel();
			dr[i].setForeground(Color.red);
			
			pp[i].add(dl[i]);
			pp[i].add(db[i]);
			pp[i].add(dr[i]);
			
			p3.add(pp[i]);
		}
		
		pt.add(p2, BorderLayout.NORTH);
		pt.add(p3, BorderLayout.CENTER);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(pt ,BorderLayout.CENTER);
		
		add(p);
		
		combo.setSelectedIndex(Integer.parseInt(f2.format(new Date()))-1);
		
		CalendarSetting();
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		combo.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				new Main();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btn1)) {
			yl.setText(Integer.toString(Integer.parseInt(yl.getText())-1));
		} else if(e.getSource().equals(btn2)) {
			yl.setText(Integer.toString(Integer.parseInt(yl.getText())+1));
		}
		
		CalendarSetting();
	}
	
	public void CalendarSetting() {
//		for(int i=0; i<42; i++) {
//			dl[i].setText("");
//			db[i].setText("");
//			dr[i].setText("");
//			db[i].setToolTipText("");
//			dr[i].setToolTipText("");
//		}
//		
//		Calendar cal = Calendar.getInstance();
//		
//		cal.set(Calendar.YEAR, Integer.parseInt(yl.getText()));
//		cal.set(Calendar.MONTH, Integer.parseInt(combo.getSelectedItem().toString())-1);
//		cal.set(Calendar.DATE, 1);
//		
//		int fd = cal.get(Calendar.DAY_OF_WEEK);
//		int ld = cal.getMaximum(cal.DATE);
//		
//		int cnt=1;
//		String dd;
//		
//		for(int i=fd-1; i<fd+ld; i++) {
//			dl[i].setText(String.format("%02d", cnt));
//			dd = yl.getText()+"-"+String.format("%02d", Integer.parseInt(combo.getSelectedItem().toString()))+"-"+dl[i].getText();
//			
//			try {
//				ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and date='"+dd+"' and division='수입'");
//				if(rs.next()) {
//					db[i].setText("수입: "+rs.getString(6)+"원");
//					if(! rs.getString(7).equals("")) {
//						db[i].setToolTipText(rs.getString(7));
//					}
//					
//				}
//				
//				rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and date='"+dd+"' and division='지출'");
//				if(rs.next()) {
//					dr[i].setText("지출: "+rs.getString(6)+"원");
//					if(! rs.getString(7).equals("")) {
//						dr[i].setToolTipText(rs.getString(7));
//					}
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			cnt++;
//		}
		
		for(int i=0; i<42; i++) {
			dl[0].setText("");
			db[0].setText("");
			dr[0].setText("");
			db[0].setToolTipText("");
			dr[0].setToolTipText("");
		}
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, Integer.parseInt(yl.getText()));
		cal.set(Calendar.MONTH, Integer.parseInt(combo.getSelectedItem().toString()));
		cal.set(Calendar.DATE, 1);
		
		int sd = cal.DAY_OF_WEEK;
		int ed = cal.getMaximum(cal.DATE);
		
		int cnt = 1;
		String dd;
		
		for(int i=0; i<sd+ed-1; i++) {
			dl[0]
			dd = 
		}
	}
}
