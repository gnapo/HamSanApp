package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import view.LinePanel;
import view.PointPanel;
import view.VisualPoint;

public class RandomButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private LinePanel lp;
	private PointPanel pp;
	private boolean circular = false;
	
	public RandomButtonListener(HamSanAlg hsa,LinePanel lp, PointPanel pp) {
		this.hsa = hsa;
		this.lp = lp;
		this.pp = pp;
		if (Math.random()<0.5) circular = true;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		doStuff();
	}
	
	public void doStuff() {
		if (circular) {
			for (int i = 0; i < 10 + Math.random()*20;i++){
				double phi = Math.random()*Math.PI*2;
				double r = Math.random()*5;
				hsa.addLine(Math.sin(phi)*r,Math.cos(phi)*r , false);
			}
			for (int i = 0; i < 10 + Math.random()*20;i++){
				double phi = Math.random()*Math.PI*2;
				double r = Math.random()*5;
				hsa.addLine(Math.sin(phi)*r,Math.cos(phi)*r , true);
			}
		}
		else {
			for (int i = 0; i < 10 + Math.random()*20;i++){
				hsa.addLine((Math.random()*10)-5, (Math.random()*15)-7.5 , false);
			}
			for (int i = 0; i < 10 + Math.random()*20;i++){
				hsa.addLine((Math.random()*10)-5, (Math.random()*15)-7.5 , true);
			}
		}
		List<VisualPoint> vpoints = hsa.getVisualPoints();
		pp.setVisualPoints(vpoints);
		lp.setVisualPoints(vpoints);
		pp.revalidate();
		pp.repaint();
		lp.revalidate();
		lp.repaint();
	}
}
