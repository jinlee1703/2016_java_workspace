package Project_Frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
	String[] bn = {"계산기","상태","메인","종료"};
	JButton[] btn = new JButton[4];
	JFrame frame;
	
	public ToolBar() {
		for(int i=0; i<4; i++) {
			String path = System.getProperty("user.dir")+"\\res\\menu\\"+bn[i]+".txt";
			path = path.replace('\\', '/');
			btn[i] = new JButton(new ImageIcon(path));
			add(btn[i]);
		}
		
		btn[0].setToolTipText("계산기를 실행시킵니다.");
		btn[1].setToolTipText("지금 상태를 보여줍니다.");
		btn[2].setToolTipText("메인메뉴로 이동합니다.");
		btn[3].setToolTipText("시스템을 종료합니다.");
	}
}
