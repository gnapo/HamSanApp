package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;

import view.LinePanel;
import view.PointPanel;
import view.VisualPoint;



public class RandomButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private LinePanel lp;
	private PointPanel pp;
	private Random r;
	private JComboBox<String> c;
	
	public RandomButtonListener(HamSanAlg hsa,LinePanel lp, PointPanel pp, JComboBox<String> c) {
		r = new Random();
		r.setSeed(1);
		this.hsa = hsa;
		this.lp = lp;
		this.pp = pp;
		this.c = c;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		doStuff();
	}
	
	public void doStuff() {
		if (c.getSelectedItem()=="random points (circle)") {
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				double phi = r.nextDouble()*Math.PI*2;
				double rad = r.nextDouble()*5;
				hsa.addLine(Math.sin(phi)*rad,Math.cos(phi)*rad , false);
			}
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				double phi = r.nextDouble()*Math.PI*2;
				double rad = r.nextDouble()*5;
				hsa.addLine(Math.sin(phi)*rad,Math.cos(phi)*rad , true);
			}
		}
		else {
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				hsa.addLine((r.nextDouble()*10)-5, (r.nextDouble()*15)-7.5 , false);
			}
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				hsa.addLine((r.nextDouble()*10)-5, (r.nextDouble()*15)-7.5 , true);
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
