package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import view.PointPanel;
import view.PointType;

public class ToggleColorButtonListener implements ActionListener {

	private PointPanel myPointPanel;
	private JLabel clabel;
	
	public ToggleColorButtonListener(PointPanel pp, JLabel clab) {
		this.myPointPanel = pp;
		this.clabel = clab;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		myPointPanel.togglePointType();
		if(myPointPanel.getCurrentType()==PointType.BLUE){
			clabel.setText("<html>Color: <font color='blue'>blue</font> - space to change</html>");
		}
		else{
			clabel.setText("<html>Color: <font color='red'>red</font> - space to change</html>");
		}
	}

}
