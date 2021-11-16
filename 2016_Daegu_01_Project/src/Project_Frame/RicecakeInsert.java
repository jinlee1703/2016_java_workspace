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
	JLabel label1 = new JLabel("상품명");
	JLabel label2 = new JLabel("재고");
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JButton btn1 = new JButton("등록");
	JButton btn2 = new JButton("취소");
	
	public RicecakeInsert() {
		setTitle("상품추가");
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
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
			} else {
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from ricecake where name='"+text1.getText()+"'");
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "이미 등록된 상품입니다.");
					} else {
						rs = DBInterface.Stmt.executeQuery("select count(*) from ricecake");
						rs.next(); String a = rs.getString(1);
						
						DBInterface.Stmt.execute("INSERT INTO `world000`.`ricecake` (`code`, `name`, `storage`) "
								+ "VALUES ('"+a+"', '"+text1.getText()+"', '"+text2.getText()+"');");
						
						JOptionPane.showMessageDialog(null, "새로운 상품이 등록되었습니다.");
						
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
