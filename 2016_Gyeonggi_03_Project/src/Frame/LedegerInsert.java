package Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class LedegerInsert extends JFrame {
	JTabbedPane tab = new JTabbedPane();
	JFormattedTextField t1 = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
	JPanel pp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	String[] cn = {"용돈","교통비","식대","생활비","통신비","의료비"};
	JComboBox combo = new JComboBox(cn);
	JFormattedTextField t2 = new JFormattedTextField(new Integer(0));
	JPanel pp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextArea t3 = new JTextArea();
	JRadioButton rb1 = new JRadioButton("수입");
	JRadioButton rb2 = new JRadioButton("지출");
	JRadioButton rb3 = new JRadioButton("현금");
	JRadioButton rb4 = new JRadioButton("카드");
	JButton btn1 = new JButton("가계부 입력");
	JButton btn2 = new JButton("초기화");
	
	public LedegerInsert() {
		setTitle("가계부 작성");
		setSize(320, 400);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		ButtonGroup g1 = new ButtonGroup();
		g1.add(rb1); g1.add(rb2);
		ButtonGroup g2 = new ButtonGroup();
		g2.add(rb3); g2.add(rb4);
		
		pp1.add(rb1); pp1.add(rb2);
		pp2.add(rb3); pp2.add(rb4);
		
		rb1.setSelected(true);
		rb3.setSelected(true);
		
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel p11= new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 20));
		p11.setBorder(new EmptyBorder(0, 10, 0, 50));
		p11.add(new JLabel("● 날짜 :")).setPreferredSize(new Dimension(100, 30));
		p11.add(t1).setPreferredSize(new Dimension(110, 20));
		p11.add(new JLabel("● 구분 :")).setPreferredSize(new Dimension(100, 30));
		p11.add(pp1).setPreferredSize(new Dimension(120, 30));
		p11.add(new JLabel("● 항목 :")).setPreferredSize(new Dimension(100, 30));
		p11.add(combo).setPreferredSize(new Dimension(110, 30));;
		p11.add(new JLabel("● 결제/수단 :")).setPreferredSize(new Dimension(100, 30));
		p11.add(pp2).setPreferredSize(new Dimension(120, 30));;
		p11.add(new JLabel("● 금액 :")).setPreferredSize(new Dimension(100, 30));
		p11.add(t2).setPreferredSize(new Dimension(110, 20));;
		
		JPanel p12 = new JPanel(new FlowLayout());
		p12.add(btn1); p12.add(btn2);
		
		p1.add(p11, BorderLayout.CENTER);
		p1.add(p12, BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(0, 10, 10, 10));
		p2.add(new JLabel("● 메모"), BorderLayout.NORTH);
		p2.add(t3, BorderLayout.CENTER);
		
		tab.addTab("가계부 입력", p1);
		tab.addTab("메모 입력", p2);
		
		add(tab);
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(t1.getText().equals("") || t2.getText().equals("")) return;
					String a = null, b=null;
					if(rb1.isSelected()) a=rb1.getText();
					if(rb2.isSelected()) a=rb2.getText();
					if(rb3.isSelected()) b=rb3.getText();
					if(rb4.isSelected()) b=rb4.getText();
					
					DBInterface.Stmt.execute("INSERT INTO `project000`.`ledger` (`memberid`, `date`, `division`, `item`, `pay`, `amount`, `memo`) "
							+ "VALUES ('"+Login.id.getText()+"', '"+t1.getText()+"', '"+a+"', '"+combo.getSelectedItem().toString()+"', '"+b+"', '"+t2.getValue()+"', '"+t3.getText()+"');");
					
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
				rb1.setSelected(true);
				rb3.setSelected(true);
			}
		});
		
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
}
