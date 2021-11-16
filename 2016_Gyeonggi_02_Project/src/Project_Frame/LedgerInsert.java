package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import Project_DBInterface.DBInterface;

public class LedgerInsert extends JFrame {
	JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
	JLabel[] l = new JLabel[6];
	String[] ln = {"날짜 :","구분 :","항목 :","결제/수단 :","금액 :","메모"};
	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	JRadioButton rb1 = new JRadioButton("수입");
	JRadioButton rb2 = new JRadioButton("지출");
	JRadioButton rb3 = new JRadioButton("현금");
	JRadioButton rb4 = new JRadioButton("카드");
	String[] cn = {"용돈","교통비","식대","생활비","통신비","의료비"};
	JComboBox combo = new JComboBox(cn);
	JTextArea t3 = new JTextArea();
	JButton btn1 = new JButton("가계부 입력");
	JButton btn2 = new JButton("초기화");
	
	public LedgerInsert() {
		setTitle("가계부 작성");
		setSize(360, 430);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		for(int i=0; i<6; i++) {
			l[i] = new JLabel("● "+ln[i]);
			l[i].setPreferredSize(new Dimension(100, 20));
		}
		t1.setPreferredSize(new Dimension(100, 20));
		t2.setPreferredSize(new Dimension(100, 20));
		combo.setPreferredSize(new Dimension(100, 30));
		
		JPanel p1 = new JPanel(new BorderLayout());
		
		JPanel p11 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 30));
		p11.setBorder(new EmptyBorder(0, 20, 20, 100));
		p11.add(l[0]); p11.add(t1);
		p11.add(l[1]); p11.add(rb1); p11.add(rb2); 
		p11.add(l[2]); p11.add(combo);
		p11.add(l[3]); p11.add(rb3); p11.add(rb4);
		p11.add(l[4]); p11.add(t2);
		
		JPanel p12 = new JPanel(new FlowLayout());
		p12.add(btn1); p12.add(btn2);
		
		p1.add(p11, BorderLayout.CENTER);
		p1.add(p12, BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(10, 10, 10, 10));
		p2.add(l[5], BorderLayout.NORTH);
		p2.add(t3, BorderLayout.CENTER);
		
		tab.addTab("가계부 입력", p1);
		tab.addTab("가계부 입력", p2);
		
		add(tab);
		
		ButtonGroup g1 = new ButtonGroup();
		g1.add(rb1); g1.add(rb2);
		ButtonGroup g2 = new ButtonGroup();
		g2.add(rb3); g2.add(rb4);
		
		t2.setText("0");
		rb1.setSelected(true);
		rb3.setSelected(true);
		
		t1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(t1.getText().length()==4) {
					if(e.getKeyChar()!='-') {
						t1.setText(t1.getText().substring(0, t1.getText().length()-1));
					}
				} else if(t1.getText().length()==7) {
					if(e.getKeyChar()!='-') {
						t1.setText(t1.getText().substring(0, t1.getText().length()-1));
					}
				} else if(t1.getText().length()==10) {	
					t1.setText(t1.getText().substring(0, t1.getText().length()-1));
				}
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(t1.getText().equals("") || t2.getText().equals("")) {
						return;
					}
					
					String a,b,c;
					
					if(rb1.isSelected()) {
						a="수입";
					} else {
						a="지출";
					}
					
					if(rb3.isSelected()) {
						b="현금";
					} else {
						b="카드";
					}
					
					if(t3.getText().equals("")) {
						c=null;
					} else {
						c="'"+t3.getText()+"'";
					}
					
					DBInterface.Stmt.execute("INSERT INTO `project000`.`ledger` (`memberid`, `date`, `division`, `item`, `pay`, `amount`, `memo`) "
							+ "VALUES ('"+Login.id.getText()+"', '"+t1.getText()+"', '"+a+"', '"+combo.getSelectedItem().toString()+"', '"+b+"', '"+t2.getText()+"', "+c+");");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t1.setText("");
				t2.setText("0");
				t3.setText("");
				combo.setSelectedIndex(0);
				rb1.setSelected(true);
				rb3.setSelected(true);
			}
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
