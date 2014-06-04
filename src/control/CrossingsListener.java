package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import view.LinePanel;

public class CrossingsListener implements ActionListener {
	
	private LinePanel lp;
	private JCheckBox myCheckBox;
	
	public CrossingsListener(JCheckBox cb, LinePanel lp) {
		this.myCheckBox = cb;
		this.lp = lp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doStuff();
	}

	public void doStuff() {
		lp.setShowCrossings(myCheckBox.isSelected());
		lp.repaint();
	}
}
