package Project_Frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ManagerMenu extends JFrame implements ActionListener {
	JButton[] btn = new JButton[7];
	String[] bn = {"상품등록","상품조회","전체 고객 조회","고객별 주문 현황","고객별 총 판매액","직책별 평균점수","승진자 명단"};
	
	public ManagerMenu() {
		setTitle("관리자 매뉴");
		setSize(320, 580);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(7, 1, 0, 25));
		p.setBorder(new EmptyBorder(20, 40, 50, 40));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			p.add(btn[i]);
		}
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new ProductInsert();
		} else if(bt==btn[1]) {
			new ProductSelect();
		} else if(bt==btn[2]) {
			new MemberSelect();
		} else if(bt==btn[3]) {
			new MemberOrderlist();
		} else if(bt==btn[4]) {
			new MemberSales();
		} else if(bt==btn[5]) {
			new WorkClassScore();
		} else if(bt==btn[6]) {
			new WorkerPromotion();
		}
	}
}
