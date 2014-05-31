package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import view.HamSanApplet;
import view.LinePanel;

public class CrossingsListener implements ActionListener {
	
	private LinePanel lp;
	private JCheckBox myCheckBox;
	private HamSanApplet hsa;
	
	public CrossingsListener(JCheckBox cb, LinePanel lp, HamSanApplet hamSanApplet) {
		this.myCheckBox = cb;
		this.lp = lp;
		hsa = hamSanApplet;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lp.setShowCrossings(myCheckBox.isSelected());
		hsa.requestFocus();
		lp.repaint();
	}

}
