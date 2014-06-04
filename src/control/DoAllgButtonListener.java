package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import view.LinePanel;
import view.PointPanel;
import view.VisualPoint;

public class DoAllgButtonListener implements ActionListener {

	private HamSanAlg h;
	private PointPanel pp;
	private LinePanel lp;
	
	public DoAllgButtonListener(HamSanAlg hsa, PointPanel pp, LinePanel lp) {
		this.h = hsa;
		this.pp = pp;
		this.lp = lp;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		doStuff();
	}
	
	public void doStuff(){
		if (h.lBlue.size()==0 || h.lRed.size() ==0) {
			return;
		}
		while (!h.done) {
			h.doAlg();
		}
		if (h.validSol(false)==false) {
			System.out.println("uh oh.");
		}
		pp.setAddingAllowed(false);
		List<VisualPoint> vpoints = h.getVisualPoints();
		pp.setVisualPoints(vpoints);
		lp.setVisualPoints(vpoints);
		pp.revalidate();
		pp.repaint();
		lp.revalidate();
		lp.repaint();
	}

}
