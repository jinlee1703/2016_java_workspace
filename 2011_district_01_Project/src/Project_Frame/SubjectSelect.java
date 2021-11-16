package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SubjectSelect extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("�����ȣ");
	JLabel label2 = new JLabel("�����");
	JLabel label3 = new JLabel("����");
	JLabel label4 = new JLabel("��米��");
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JComboBox combo = new JComboBox();
	JButton btn1 = new JButton("�߰�");
	JButton btn2 = new JButton("����");
	JButton btn3 = new JButton("�ʱ�ȭ");
	String[] header = {"�����ȣ","�����","����","��米��"}; 
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public SubjectSelect() {
		setTitle("�л����");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		new MenuBar(this);
		
		JPanel p = new JPanel(new FlowLayout());
		
		JPanel p1 = new JPanel(new GridLayout(4, 2, 5, 1));
		p1.setPreferredSize(new Dimension(495, 150));
		p1.setBorder(BorderFactory.createTitledBorder("�л� ����"));
		label1.setHorizontalAlignment(label1.RIGHT);
		label2.setHorizontalAlignment(label2.RIGHT);
		label3.setHorizontalAlignment(label3.RIGHT);
		label4.setHorizontalAlignment(label4.RIGHT);
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		p1.add(label3); p1.add(text3);
		p1.add(label4); p1.add(combo);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setPreferredSize(new Dimension(500, 33));
		p2.add(btn1); p2.add(btn2); p2.add(btn3);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setPreferredSize(new Dimension(495, 250));
		p3.setBorder(BorderFactory.createTitledBorder("�л� ���"));
		p3.add(scroll);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				text1.setText((String) table.getValueAt(table.getSelectedRow(), 0));
				text2.setText((String) table.getValueAt(table.getSelectedRow(), 1));
				text3.setText((String) table.getValueAt(table.getSelectedRow(), 2));
				combo.setSelectedItem((String) table.getValueAt(table.getSelectedRow(), 3));
				
				btn1.setText("����");
			}
		});
		
		p.add(p1); p.add(p2); p.add(p3);
		
		add(p);
		
		InitSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
