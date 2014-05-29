package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.PointPanel;
import view.PointType;

public class ResetButtonListener implements ActionListener {

	private HamSanAlg hsa;
	PointPanel pp;
	
	public ResetButtonListener(HamSanAlg hsa, PointPanel pp) {
		this.hsa = hsa;
		this.pp = pp;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		hsa.init();
		pp.setAddingAllowed(true);
		pp.setVisualPoints(hsa.getVisualPoints());
		pp.refreshAll();
		pp.setCurrentType(PointType.BLUE);
	}

}
