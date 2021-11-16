package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class ClientUpdate extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[7];
	String[] ln = {"고객번호","고객ID","고객명","주소","고객PW","전화번호","가입일"};
	JTextField[] text = new JTextField[7];
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("취소");
	
	public ClientUpdate() {
		setTitle("고객 정보 수정");
		setSize(465, 175);
		setResizable(false);
		setLocationRelativeTo(null);
		
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(110, 20));
			label[i].setHorizontalAlignment(label[i].RIGHT);
			text[i] = new JTextField(9);
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new GridLayout(4, 2, 5, 5));
		for(int i=0; i<4; i++) {
			p1.add(label[i]);
			p1.add(text[i]);
		}
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 5, 5));
		p2.setBorder(new EmptyBorder(20, 0, 0, 0));
		for(int i=4; i<7; i++) {
			p2.add(label[i]);
			p2.add(text[i]);
		}
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.setBorder(new EmptyBorder(0, -1, 0, 0));
		p3.add(p1); p3.add(p2);
		
		JPanel p4 = new JPanel(new FlowLayout());
		p4.add(btn1); p4.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p3, BorderLayout.CENTER);
		basePanel.add(p4, BorderLayout.SOUTH);
		
		add(basePanel);
		
		TextSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			if(text[3].getText().equals("") || text[4].getText().equals("") || text[5].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "실패하였습니다.", "수정 실패!", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					DBInterface.Stmt.execute("UPDATE `world000`.`client` SET `pw`='"+text[4].getText()+"', `phnumber`='"+text[5].getText()+"', `address`='"+text[3].getText()+"' WHERE `code`='"+text[0].getText()+"';");
					
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
					
					ClientSelect.TableSetting();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (btn==btn2) {
			this.dispose();
		}
	}
	
	public void TextSetting() {
		if(Login.idText.getText().equals("admin")) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where code='"+ClientSelect.table.getValueAt(ClientSelect.table.getSelectedRow(), 0)+"'");
				rs.next();
				
				text[0].setText(rs.getString(1));
				text[1].setText(rs.getString(2));
				text[2].setText(rs.getString(4));
				text[3].setText(rs.getString(6));
				text[4].setText(rs.getString(3));
				text[5].setText(rs.getString(5));
				text[6].setText(rs.getString(7));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from client where id='"+Login.idText.getText()+"'");
				
				if(rs.next()) {
					text[0].setText(rs.getString(1));
					text[1].setText(rs.getString(2));
					text[2].setText(rs.getString(4));
					text[3].setText(rs.getString(6));
					text[4].setText(rs.getString(3));
					text[5].setText(rs.getString(5));
					text[6].setText(rs.getString(7));
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
		text[0].setEnabled(false);
		text[1].setEnabled(false);
		text[2].setEnabled(false);
		text[6].setEnabled(false);
	}
}
