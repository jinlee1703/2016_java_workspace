package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class SalesSelect extends JFrame implements ActionListener {
	JButton btn1 = new JButton("���Ϸ�����");
	JButton btn2 = new JButton("���ư���");
	String[] header = {"�ֹ���ȣ","�ֹ�����","��ǰ��","�ֹ���","�ֹ���ID","�ֹ��ڸ�"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public SalesSelect() {
		setTitle("�ǸŰ���");
		setSize(450, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(btn1); p1.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(BorderFactory.createTitledBorder("�ֹ�����"));
		p2.add(scroll);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			
		} else {
			this.dispose();
		}
	}
	
	public void FileSave() throws Exception {
		FileDialog fd = new FileDialog(this, "�ؽ�Ʈ���Ϸ�����", FileDialog.SAVE);
		fd.setVisible(true);
		
		FileWriter fw = new FileWriter(fd.getDirectory()+fd.getFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		
		String str = s.format(d);
		bw.write(str); bw.newLine();
		
		str = "�ֹ�����Ʈ";
		bw.write(str); bw.newLine(); bw.newLine();
		
		str = "��ȣ\t�֒L��¥\t���̸�\t����\t��ID\t����";
		bw.write(str); bw.newLine(); bw.newLine();
		
		ResultSet rs = DBInterface.Stmt.executeQuery("SELECT sales.code,sales.salesdate,ricecake.name,sales.amount,client.id,client.name FROM sales join ricecake join client on sales.fk_ricecakecode=ricecake.code and sales.fk_clientcode=client.code order by code desc;");
		
		while(rs.next()) {
			bw.newLine();
			str = rs.getString(0)+"\t"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5);
			bw.write(str);
		}
		bw.close();
	}
}
