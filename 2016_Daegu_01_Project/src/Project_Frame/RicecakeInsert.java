package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Project_DBInterface.DBInterface;

public class RicecakeInsert extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("��ǰ��");
	JLabel label2 = new JLabel("���");
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JButton btn1 = new JButton("���");
	JButton btn2 = new JButton("���");
	
	public RicecakeInsert() {
		setTitle("��ǰ�߰�");
		setSize(240, 120);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(2, 2, 5, 5));
		label1.setHorizontalAlignment(label1.RIGHT);
		label2.setHorizontalAlignment(label2.RIGHT);
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(btn1); p2.add(btn2);
		
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
			if(text1.getText().equals("") || text2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��� �Է����ּ���.");
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from ricecake where name='"+text1.getText()+"'");
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "�̹� ��ϵ� ��ǰ�Դϴ�.");
					} else {
						rs = DBInterface.Stmt.executeQuery("select count(*) from ricecake");
						rs.next(); String a = rs.getString(1);
						
						DBInterface.Stmt.execute("INSERT INTO `world000`.`ricecake` (`code`, `name`, `storage`) "
								+ "VALUES ('"+a+"', '"+text1.getText()+"', '"+text2.getText()+"');");
						
						JOptionPane.showMessageDialog(null, "���ο� ��ǰ�� ��ϵǾ����ϴ�.");
						
						RicecakeSelect.TableSetting();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			this.dispose();
		}
	}
}
