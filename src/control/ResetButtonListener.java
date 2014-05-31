package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.LinePanel;
import view.PointPanel;
import view.PointType;

public class ResetButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private PointPanel pp;
	private LinePanel lp;
	
	public ResetButtonListener(HamSanAlg hsa, PointPanel pp, LinePanel lp) {
		this.hsa = hsa;
		this.pp = pp;
		this.lp = lp;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		hsa.init();
		pp.setAddingAllowed(true);
		pp.setVisualPoints(hsa.getVisualPoints());
		lp.setMinAndMax(-10, -10, 10, 10);
		pp.refreshAll();
		pp.setCurrentType(PointType.BLUE);
	}

}
