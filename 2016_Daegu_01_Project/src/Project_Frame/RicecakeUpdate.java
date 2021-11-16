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

public class RicecakeUpdate extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("상품명");
	JLabel label2 = new JLabel("수량");
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("취소");
	
	public RicecakeUpdate() {
		setTitle("상품명");
		setSize(240, 120);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(2, 2));
		p1.add(label1); p1.add(text1);
		p1.add(label2); p1.add(text2);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(btn1); p2.add(btn2);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		TextSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			if(text2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "수량을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from ricecake where name='"+text1.getText()+"'");
					rs.next(); String a = rs.getString(1);
					DBInterface.Stmt.execute("UPDATE `world000`.`ricecake` SET `storage`='"+text2.getText()+"' WHERE `code`='"+a+"';");
					
					RicecakeSelect.TableSetting();
					
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			this.dispose();
		}
	}
	
	public void TextSetting() {
		text1.setText((String) RicecakeSelect.table.getValueAt(RicecakeSelect.table.getSelectedRow(), 1));
		text1.setEnabled(false);
	}
}
