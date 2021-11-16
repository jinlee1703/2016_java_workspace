package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
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

import Project_DBInterface.DBInterface;

public class ledgerInsert extends JFrame implements ActionListener {
	JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
	JPanel[] pp = new JPanel[5];
	JLabel[] l = new JLabel[5];
	String[] ln = {"�� ��¥ :","�� ���� :","�� �׸� : :","�� ����/���� :","�� �ݾ� :"};
	JTextField text1 = new JTextField();
	JRadioButton rb1 = new JRadioButton("����");
	JRadioButton rb2 = new JRadioButton("����");
	String[] cn = {"�뵷","�����","�Ĵ�","��Ȱ��","��ź�","�Ƿ��"};
	JComboBox combo = new JComboBox(cn);
	JRadioButton rb3 = new JRadioButton("����");
	JRadioButton rb4 = new JRadioButton("ī��");
	JTextField text2 = new JTextField();
	JButton btn1 = new JButton("����� �Է�");
	JButton btn2 = new JButton("�ʱ�ȭ");
	JLabel label = new JLabel("�� �޸�");
	JTextArea memo = new JTextArea();
	
	
	public ledgerInsert() {
		setTitle("����� �ۼ�");
		setSize(350, 430);
		setLocationRelativeTo(null);
		
		new ToolBar(this);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 30));
		p1.setBorder(new EmptyBorder(0, 20, 0, 50));
		ButtonGroup g1 = new ButtonGroup();
		g1.add(rb1); g1.add(rb2);
		ButtonGroup g2 = new ButtonGroup();
		g2.add(rb3); g2.add(rb4);
		for(int i=0; i<5; i++) {
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(100, 20));
		}
		text1.setPreferredSize(new Dimension(100, 20));
		text2.setPreferredSize(new Dimension(100, 20));
		combo.setPreferredSize(new Dimension(100, 30));
		
		p1.add(l[0]); p1.add(text1);
		p1.add(l[1]); p1.add(rb1); p1.add(rb2);
		p1.add(l[2]); p1.add(combo);
		p1.add(l[3]); p1.add(rb3); p1.add(rb4);
		p1.add(l[4]); p1.add(text2);
		
		JPanel p2 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		p2.add(btn1); p2.add(btn2);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		rb1.setSelected(true);
		rb3.setSelected(true);;
		text2.setText("0");
		
		tab.addTab("����� �Է�", p);
		
		JPanel p3 = new JPanel(new BorderLayout());
		memo.setLineWrap(true);
		p3.setBorder(new EmptyBorder(10, 10, 10, 10));
		p3.add(label, BorderLayout.NORTH);
		p3.add(memo, BorderLayout.CENTER);
		
		tab.addTab("�޸� �Է�", p3);
		
		add(tab);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
			try {
				String d;
				if(rb1.isSelected()==true) {
					d = "����";
				} else {
					d = "����";
				}
				
				String p;
				if(rb3.isSelected()==true) {
					p = "����";
				} else {
					p = "ī��";
				}
				if(memo.getText().equals("")) {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`memberid`, `date`, `division`, `item`, `pay`, `amount`) "
							+ "VALUES ('"+Login.id.getText()+"', '"+text1.getText()+"', '"+d+"', '"+combo.getSelectedItem().toString()+"', '"+p+"', '"+Integer.parseInt(text2.getText())+"');");
				} else {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`memberid`, `date`, `division`, `item`, `pay`, `amount`, `memo`) VALUES ('"+Login.id.getText()+"', '"+text1.getText()+"', '"+d+"', '"+combo.getSelectedItem().toString()+"', '"+p+"', '"+Integer.parseInt(text2.getText())+"', '"+memo.getText()+"');");
					
					try {
						CalendarSelect.CalendarInit();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if(btn==btn1) {
			
		} else {
			text1.setText("");
			rb1.setSelected(true);
			combo.setSelectedIndex(0);
			rb3.setSelected(true);
			text2.setText("0");
			memo.setText("");
		}
	}
}
