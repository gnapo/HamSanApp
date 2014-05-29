package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.PointPanel;

public class ButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private PointPanel pp;
	
	public ButtonListener(HamSanAlg h, PointPanel pp) {
		this.hsa = h;
		this.pp = pp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		hsa.doAlg();
		pp.setAddingAllowed(false);
	}

}
