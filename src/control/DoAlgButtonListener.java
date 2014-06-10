package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import view.HamSanApplet;
import view.LinePanel;
import view.PointPanel;
import view.VisualPoint;

public class DoAlgButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private PointPanel pp;
	private LinePanel lp;
	private JLabel l;
	private HamSanApplet applet;
	
	public DoAlgButtonListener(HamSanAlg h, PointPanel pp, LinePanel lp, JLabel label, HamSanApplet hamSanApplet) {
		this.hsa = h;
		this.pp = pp;
		this.lp = lp;
		this.l = label;
		this.applet = hamSanApplet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doStuff();
	}
	
	public void doStuff() {
		hsa.doAlg();
		int step = hsa.step;
		switch (step) {
		case 1:
			l.setText("step 1: divided in intervals");
			break;
		case 2:
			l.setText("step 2: found interval with odd intersection property");
			break;
		case 3:
			l.setText("step 3: construted trapeze");
			break;
		case 0:
			l.setText("step 4: removed lines outside the trapeze");
			break;
		}
		if (hsa.getVisualPoints().size()==0) {
			l.setText("step 0: place points");
		} else {
			applet.setPlacingEnabled(false);
		}
		if (hsa.done) {
			if (hsa.validSol(false)){
				l.setText("found valid solution!");
			}
			else {
				l.setText("found invalid solution");
			}
		}
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
