package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.LinePanel;

public class ResetZoomListener implements ActionListener {
	
	private LinePanel lp;
	
	public ResetZoomListener(LinePanel lp) {
		this.lp = lp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lp.setMinAndMax(-10, -10, 10, 10);
		lp.repaint();
	}

}
