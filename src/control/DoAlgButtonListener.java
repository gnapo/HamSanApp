package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import view.LinePanel;
import view.PointPanel;
import view.VisualPoint;

public class DoAlgButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private PointPanel pp;
	private LinePanel lp;
	
	public DoAlgButtonListener(HamSanAlg h, PointPanel pp, LinePanel lp) {
		this.hsa = h;
		this.pp = pp;
		this.lp = lp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doStuff();
	}
	
	public void doStuff() {
		hsa.doAlg();
		lp.followTrapeze();
		pp.setAddingAllowed(false);
		List<VisualPoint> vpoints = hsa.getVisualPoints();
		pp.setVisualPoints(vpoints);
		lp.setVisualPoints(vpoints);
		pp.revalidate();
		pp.repaint();
		lp.revalidate();
		lp.repaint();
	}

}
