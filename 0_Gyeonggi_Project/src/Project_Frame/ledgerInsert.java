package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import Project_DBInterface.DBInterface;

public class ledgerInsert extends JFrame {
	JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
	JLabel[] l = new JLabel[5];
	String[] ln = {"날짜","구분","항목","결제/수단","금액"};
	JFormattedTextField t1 = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
	JFormattedTextField t2 = new JFormattedTextField(new Integer(0));
	String[] cn = {"용단","교통비","식대","생활비","통신비","의료비"};
	JComboBox combo = new JComboBox(cn);
	JRadioButton rb1 = new JRadioButton("수입");
	JRadioButton rb2 = new JRadioButton("지출");
	JRadioButton rb3 = new JRadioButton("현금");
	JRadioButton rb4 = new JRadioButton("카드");
	JButton btn1 = new JButton("가계부 입력");
	JButton btn2 = new JButton("초기화");
	JTextArea t3 = new JTextArea();
	
	public ledgerInsert() {
		setTitle("가계부 작성");
		setSize(350, 450);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(new EmptyBorder(20, 15, 0, 50));
		JPanel p11= new JPanel(new GridLayout(5, 1, 20, 20));
		for(int i=0; i<5; i++) {
			l[i] = new JLabel("●"+ln[i]+" :");
			p11.add(l[i]);
		}
		p1.add(p11, BorderLayout.WEST);
		JPanel p12 = new JPanel(new GridLayout(5, 1, 20, 20));
		JPanel rp1 = new JPanel();
		rp1.add(rb1); rp1.add(rb2);
		rb1.setActionCommand(rb1.getText());
		rb2.setActionCommand(rb2.getText());
		JPanel rp2 = new JPanel();
		rp2.add(rb3); rp2.add(rb4);
		rb3.setActionCommand(rb3.getText());
		rb4.setActionCommand(rb4.getText());
		p12.add(t1); p12.add(rp1); p12.add(combo); p12.add(rp2); p12.add(t2);
		p1.add(p12, BorderLayout.CENTER);
		JPanel p13 = new JPanel(new FlowLayout());
		p13.setBorder(new EmptyBorder(20, 0, 0, 0));
		p13.add(btn1); p13.add(btn2);
		p1.add(p13, BorderLayout.SOUTH);
		tab.add("가계부 입력", p1);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(0, 10, 10, 10));
		p2.add(new JLabel("● 메모"), BorderLayout.NORTH);
		p2.add(t3, BorderLayout.CENTER);
		tab.add("메모 입력", p2);
		
		ButtonGroup g1 = new ButtonGroup();
		g1.add(rb1); g1.add(rb2);
		
		ButtonGroup g2 = new ButtonGroup();
		g2.add(rb3); g2.add(rb4);
		
		add(tab);
		
		rb1.setSelected(true);
		rb3.setSelected(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "A");
				new Main();
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
					DBInterface.Stmt.execute("INSERT INTO `project000`.`ledger` (`memberid`, `date`, `division`, `item`, `pay`, `amount`, `memo`) VALUES ('"+Login.id.getText()+"', '"+t1.getText()+"', '"+g1.getSelection().getActionCommand()+"', '"+combo.getSelectedItem().toString()+"', '"+g2.getSelection().getActionCommand()+"', '"+t2.getValue()+"', '"+t3.getText()+"');");
					
					dispose();
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
				t2.setText("");
				t3.setText("");
				combo.setSelectedIndex(0);
				rb1.setSelected(true);
				rb3.setSelected(true);
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}

