package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Project_DBInterface.DBInterface;

public class Join extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[6];
	String[] ln = {"���̵� :","��й�ȣ :","��й�ȣ Ȯ�� :","�̸� :","��ȭ��ȣ :","�ּ� :"};
	JTextField[] text = new JTextField[6];
	JButton btn1 = new JButton("Ȯ��");
	JButton btn2 = new JButton("���");
	
	public Join() {
		setTitle("ȸ������");
		setSize(250, 280);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(6, 2, 10, 10));
		p1.setBorder(BorderFactory.createTitledBorder("ȸ������ ���"));
		
		text[0] = new JTextField();
		text[1] = new JPasswordField();
		text[2] = new JPasswordField();
		text[3] = new JTextField();
		text[4] = new JTextField();
		text[5] = new JTextField();
		
		for(int i=0; i<6; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setHorizontalAlignment(label[i].RIGHT);
			p1.add(label[i]);
			p1.add(text[i]);
		}
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(btn1); p2.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			if(text[0].getText().equals("") || text[1].getText().equals("") || text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("") || text[5].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��� �Է����ּ���.");
			} else if(text[1].getText().equals(text[2].getText())) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where id='"+text[0].getText()+"'");
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "�ߺ��� ���̵� �ֽ��ϴ�.");
					} else {
						ResultSet rss = DBInterface.Stmt.executeQuery("select count(*) from client");
						int a = rss.getInt(1)+1;
						Date d = new Date();
						SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
						
						DBInterface.Stmt.execute("INSERT INTO `world000`.`client` (`code`, `id`, `pw`, `name`, `phnumber`, `address`, `RegiDate`) "
								+ "VALUES ('"+a+"', '"+text[0]+"', '"+text[1]+"', '"+text[3]+"', '"+text[4]+"', '"+text[5]+"', '"+s.format(d)+"');");
						JOptionPane.showMessageDialog(null, "���ο� ȸ���� ��ϵǾ����ϴ�.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(btn==btn2) {
			this.dispose();
		}
	}
}
